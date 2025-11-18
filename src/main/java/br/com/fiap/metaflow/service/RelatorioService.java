package br.com.fiap.metaflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.metaflow.exception.RecursoNaoEncontradoException;
import br.com.fiap.metaflow.model.Checkin;
import br.com.fiap.metaflow.model.Habilidade;
import br.com.fiap.metaflow.model.Meta;
import br.com.fiap.metaflow.model.Usuario;
import br.com.fiap.metaflow.repository.CheckinRepository;
import br.com.fiap.metaflow.repository.MetaRepository;
import br.com.fiap.metaflow.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class RelatorioService {
    private final UsuarioRepository usuarioRepository;
    private final CheckinRepository checkinRepository;
    private final MetaRepository metaRepository;

    @Autowired
    public RelatorioService(UsuarioRepository usuarioRepository, CheckinRepository checkinRepository,
                          MetaRepository metaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.checkinRepository = checkinRepository;
        this.metaRepository = metaRepository;
    }

    private static class DadosUsuario {
        private final Usuario usuario;
        private final List<Checkin> checkins;
        private final List<Meta> metas;
        private final List<Habilidade> habilidades;

        public DadosUsuario(Usuario usuario, List<Checkin> checkins, List<Meta> metas, List<Habilidade> habilidades) {
            this.usuario = usuario;
            this.checkins = checkins;
            this.metas = metas;
            this.habilidades = habilidades;
        }

        public Usuario usuario() { return usuario; }
        public List<Checkin> checkins() { return checkins; }
        public List<Meta> metas() { return metas; }
        public List<Habilidade> habilidades() { return habilidades; }
    }

    @Transactional
    private DadosUsuario buscarDadosDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        List<Checkin> checkins = usuario.getCheckins();
        List<Meta> metas = usuario.getMetas();
        List<Habilidade> habilidades = usuario.getHabilidades();

        return new DadosUsuario(usuario, checkins, metas, habilidades);
    }

    public String gerarRelatorioPersonalizado(Long usuarioId) {
        DadosUsuario dados = buscarDadosDoUsuario(usuarioId);
        Usuario usuario = dados.usuario();
        List<Checkin> checkins = dados.checkins();
        List<Meta> metas = dados.metas();
        List<Habilidade> habilidades = dados.habilidades();

        double humorMedio = checkins.stream().mapToInt(Checkin::getHumor).average().orElse(0);
        double estresseMedio = checkins.stream().mapToInt(Checkin::getNivelEstresse).average().orElse(0);
        double sonoMedio = checkins.stream().mapToInt(Checkin::getQualidadeSono).average().orElse(0);

        long metasConcluidas = metas.stream().filter(m -> m.getStatus() == Meta.Status.CONCLUIDA).count();
        long metasAtivas = metas.stream().filter(m -> m.getStatus() == Meta.Status.ATIVA).count();
        long habilidadesAprendizado = habilidades.stream().filter(Habilidade::getEmAprendizado).count();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE BEM-ESTAR - ").append(usuario.getNome()).append(" ===\n\n");
        relatorio.append(" DADOS RECENTES:\n");
        relatorio.append(String.format("- Humor médio: %.1f/5\n", humorMedio));
        relatorio.append(String.format("- Nível médio de estresse: %.1f/5\n", estresseMedio));
        relatorio.append(String.format("- Qualidade média do sono: %.1f/5\n", sonoMedio));
        relatorio.append(String.format("- Metas ativas: %d\n", metasAtivas));
        relatorio.append(String.format("- Metas concluídas: %d\n", metasConcluidas));
        relatorio.append(String.format("- Habilidades em aprendizado: %d\n\n", habilidadesAprendizado));

        relatorio.append(" ANÁLISE E RECOMENDAÇÕES:\n");
        
        if (humorMedio < 3) {
            relatorio.append("- Seu humor está baixo. Considere atividades de lazer e descanso.\n");
        } else {
            relatorio.append("- Seu humor está bom! Continue mantendo o equilíbrio.\n");
        }
        
        if (estresseMedio > 3) {
            relatorio.append("- Níveis de estresse elevados. Pratique técnicas de relaxamento.\n");
        } else {
            relatorio.append("- Seus níveis de estresse estão controlados. Ótimo trabalho!\n");
        }
        
        if (sonoMedio < 3) {
            relatorio.append("- Qualidade do sono pode melhorar. Mantenha uma rotina regular.\n");
        } else {
            relatorio.append("- Sua qualidade de sono está boa. Continue com os bons hábitos!\n");
        }
        
        if (metasConcluidas > 0) {
            relatorio.append(String.format("- Parabéns! Você concluiu %d meta(s). Continue assim!\n", metasConcluidas));
        }
        
        relatorio.append("\n PRÓXIMOS PASSOS SUGERIDOS:\n");
        relatorio.append("- Mantenha a consistência nos check-ins diários\n");
        relatorio.append("- Revise suas metas ativas regularmente\n");
        relatorio.append("- Equilibre tempo entre trabalho, aprendizado e lazer\n");
        relatorio.append("- Celebre cada conquista, por menor que seja\n");

        return relatorio.toString();
    }

    public String gerarRecomendacoes(Long usuarioId) {
        DadosUsuario dados = buscarDadosDoUsuario(usuarioId);
        Usuario usuario = dados.usuario();
        List<Meta> metas = dados.metas();
        List<Habilidade> habilidades = dados.habilidades();

        StringBuilder recomendacoes = new StringBuilder();
        recomendacoes.append("=== RECOMENDAÇÕES DE CARREIRA - ").append(usuario.getNome()).append(" ===\n\n");
        recomendacoes.append("Objetivo de carreira: ").append(usuario.getObjetivoCarreira()).append("\n\n");

        recomendacoes.append(" RECURSOS SUGERIDOS:\n");
        
        boolean temTecnologia = habilidades.stream().anyMatch(h -> "Tecnologia".equalsIgnoreCase(h.getCategoria()));
        boolean temGestao = habilidades.stream().anyMatch(h -> "Gestão".equalsIgnoreCase(h.getCategoria()));
        boolean temSaude = metas.stream().anyMatch(m -> m.getCategoria() == Meta.Categoria.SAUDE);

        if (temTecnologia) {
            recomendacoes.append("- Plataformas tech: Alura, RocketSeat, Udemy\n");
            recomendacoes.append("- Cursos: Java Spring Boot, React, Cloud Computing\n");
        }
        
        if (temGestao) {
            recomendacoes.append("- Cursos: Gestão de Projetos, Liderança, Scrum\n");
            recomendacoes.append("- Livros: 'A Arte da Guerra', 'Scrum'\n");
        }
        
        if (temSaude) {
            recomendacoes.append("- Apps: Headspace (meditação), MyFitnessPal (nutrição)\n");
            recomendacoes.append("- Atividades: Yoga, Pilates, Corrida\n");
        }

        // Recomendações gerais
        recomendacoes.append("\n PLATAFORMAS RECOMENDADAS:\n");
        recomendacoes.append("- Coursera: Cursos de universidades internacionais\n");
        recomendacoes.append("- LinkedIn Learning: Desenvolvimento profissional\n");
        recomendacoes.append("- YouTube EDU: Conteúdo gratuito de qualidade\n");

        recomendacoes.append("\n COMPETÊNCIAS DO FUTURO:\n");
        recomendacoes.append("- Inteligência Emocional\n");
        recomendacoes.append("- Adaptabilidade\n"); 
        recomendacoes.append("- Resolução de Problemas Complexos\n");
        recomendacoes.append("- Comunicação Eficaz\n");
        recomendacoes.append("- Pensamento Crítico\n");

        return recomendacoes.toString();
    }

    public String gerarAnaliseEngajamento() {
        long totalUsuarios = usuarioRepository.count();
        long totalCheckins = checkinRepository.count();
        long totalMetas = metaRepository.count();
        long metasConcluidas = metaRepository.countByStatus(Meta.Status.CONCLUIDA);

        double mediaCheckinsPorUsuario = totalUsuarios > 0 ? (double) totalCheckins / totalUsuarios : 0;
        double taxaConclusaoMetas = totalMetas > 0 ? (double) metasConcluidas / totalMetas * 100 : 0;

        StringBuilder analise = new StringBuilder();
        analise.append("=== RELATÓRIO DE ENGAJAMENTO - METAFLOW ===\n\n");
        analise.append(" DADOS GERAIS:\n");
        analise.append(String.format("- Usuários registrados: %d\n", totalUsuarios));
        analise.append(String.format("- Total de check-ins: %d\n", totalCheckins));
        analise.append(String.format("- Check-ins médios por usuário: %.2f\n", mediaCheckinsPorUsuario));
        analise.append(String.format("- Metas totais: %d (%.1f%% concluídas)\n", totalMetas, taxaConclusaoMetas));

        analise.append("\n ANÁLISE:\n");
        if (mediaCheckinsPorUsuario > 5) {
            analise.append("-  Excelente engajamento! Usuários estão usando o app consistentemente.\n");
        } else if (mediaCheckinsPorUsuario > 2) {
            analise.append("-  Bom engajamento. Espaço para crescimento na frequência de uso.\n");
        } else {
            analise.append("-  Engajamento baixo. Considere features para aumentar retenção.\n");
        }

        if (taxaConclusaoMetas > 50) {
            analise.append("-  Alta taxa de sucesso nas metas! Usuários estão alcançando objetivos.\n");
        } else {
            analise.append("-  Oportunidade para melhorar suporte ao cumprimento de metas.\n");
        }

        analise.append("\n SUGESTÕES DE MELHORIA:\n");
        analise.append("-  Notificações para check-ins diários\n");
        analise.append("-  Gamificação com conquistas e recompensas\n");
        analise.append("-  Relatórios visuais mais detalhados\n");
        analise.append("-  Comunidade para compartilhar progresso\n");
        analise.append("-  Metas em grupo para aumentar motivação\n");

        return analise.toString();
    }
}