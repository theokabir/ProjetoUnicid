-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 06-Maio-2023 às 18:36
-- Versão do servidor: 8.0.31
-- versão do PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projeto`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aluno`
--

DROP TABLE IF EXISTS `aluno`;
CREATE TABLE IF NOT EXISTS `aluno` (
  `rgm` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  `nascimento` varchar(255) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `municipio` varchar(255) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `celular` varchar(255) NOT NULL,
  `curso` varchar(255) DEFAULT NULL,
  `campus` varchar(255) DEFAULT NULL,
  `periodo` enum('Matutino','Vespertino','Noturno') DEFAULT NULL,
  PRIMARY KEY (`rgm`),
  UNIQUE KEY `rgm` (`rgm`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Extraindo dados da tabela `aluno`
--

INSERT INTO `aluno` (`rgm`, `nome`, `nascimento`, `cpf`, `email`, `endereco`, `municipio`, `uf`, `celular`, `curso`, `campus`, `periodo`) VALUES
(1, 'theo', '12/31/2312', '121.212.321-12', '212123123', '12121212', '12121231212', 'CE', '(12) 12312-3121', 'Design', 'Pinheiros', 'Vespertino');

-- --------------------------------------------------------

--
-- Estrutura da tabela `nota`
--

DROP TABLE IF EXISTS `nota`;
CREATE TABLE IF NOT EXISTS `nota` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rgm_aluno` int NOT NULL,
  `disciplina` varchar(255) NOT NULL,
  `semestre` varchar(255) NOT NULL,
  `nota` float NOT NULL,
  `faltas` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `rgm_aluno` (`rgm_aluno`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

--
-- Extraindo dados da tabela `nota`
--

INSERT INTO `nota` (`id`, `rgm_aluno`, `disciplina`, `semestre`, `nota`, `faltas`) VALUES
(7, 1, 'desenho', '2023-1', 7, 4),
(16, 1, 'ergonomia', '2023-1', 7.5, 2),
(18, 1, 'desenho', '2019-2', 9.5, 0);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `nota`
--
ALTER TABLE `nota`
  ADD CONSTRAINT `nota_ibfk_1` FOREIGN KEY (`rgm_aluno`) REFERENCES `aluno` (`rgm`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
