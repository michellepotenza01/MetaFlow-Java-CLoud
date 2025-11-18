package br.com.fiap.metaflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.metaflow.service.RelatorioService;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Autowired
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/personalizado/{usuarioId}")
    public ResponseEntity<String> gerarRelatorioPersonalizado(@PathVariable Long usuarioId) {
        try {
            String relatorio = relatorioService.gerarRelatorioPersonalizado(usuarioId);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/recomendacoes/{usuarioId}")
    public ResponseEntity<String> gerarRecomendacoes(@PathVariable Long usuarioId) {
        try {
            String recomendacoes = relatorioService.gerarRecomendacoes(usuarioId);
            return ResponseEntity.ok(recomendacoes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao gerar recomendações: " + e.getMessage());
        }
    }

    @GetMapping("/engajamento")
    public ResponseEntity<String> gerarAnaliseEngajamento() {
        try {
            String analise = relatorioService.gerarAnaliseEngajamento();
            return ResponseEntity.ok(analise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao gerar análise de engajamento: " + e.getMessage());
        }
    }
}