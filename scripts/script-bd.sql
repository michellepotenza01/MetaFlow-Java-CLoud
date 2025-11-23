-- =============================================
-- SCRIPT DE CRIAÇÃO DO BANCO METAFLOW
-- Azure SQL Database
-- Requisito da matéria: arquivo script-bd.sql na pasta /scripts
-- =============================================

-- Tabela de Usuários
CREATE TABLE mf_usuarios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    titulo_profissional NVARCHAR(255) NOT NULL,
    objetivo_carreira NVARCHAR(255) NOT NULL,
    data_criacao DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Tabela de Metas
CREATE TABLE mf_metas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    titulo NVARCHAR(50) NOT NULL,
    categoria NVARCHAR(20) NOT NULL,
    descricao NVARCHAR(200),
    valor_alvo INT NOT NULL,
    valor_atual INT NOT NULL,
    prazo DATE NOT NULL,
    status NVARCHAR(20) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES mf_usuarios(id) ON DELETE CASCADE
);

-- Tabela de Checkins (Acompanhamentos Diários)
CREATE TABLE mf_checkins (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    data DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    humor INT NOT NULL,
    qualidade_sono INT NOT NULL,
    nivel_estresse INT NOT NULL,
    produtividade INT NOT NULL,
    tempo_trabalho INT NOT NULL,
    tempo_aprendizado INT NOT NULL,
    tempo_lazer INT NOT NULL,
    anotacoes NVARCHAR(200),
    FOREIGN KEY (usuario_id) REFERENCES mf_usuarios(id) ON DELETE CASCADE
);

-- Tabela de Habilidades
CREATE TABLE mf_habilidades (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    nome NVARCHAR(50) NOT NULL,
    categoria NVARCHAR(50) NOT NULL,
    nivel_atual INT NOT NULL,
    nivel_desejado INT NOT NULL,
    em_aprendizado BIT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES mf_usuarios(id) ON DELETE CASCADE
);

-- =============================================
-- CONFIRMAÇÃO DE CRIAÇÃO
-- =============================================

SELECT 'Tabelas criadas com sucesso: mf_usuarios, mf_metas, mf_checkins, mf_habilidades' as Status;