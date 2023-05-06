-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 06-Maio-2023 às 18:26
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
-- Banco de dados: `projetounicid`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aluno`
--

DROP TABLE IF EXISTS `aluno`;
CREATE TABLE IF NOT EXISTS `aluno` (
  `rgm` int NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `nascimento` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endereco` varchar(500) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `celular` varchar(11) DEFAULT NULL,
  `curso` varchar(255) DEFAULT NULL,
  `periodo` enum('matutino','vespertino','noturno') DEFAULT NULL,
  PRIMARY KEY (`rgm`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `aluno`
--

INSERT INTO `aluno` (`rgm`, `nome`, `nascimento`, `cpf`, `email`, `endereco`, `municipio`, `uf`, `celular`, `curso`, `periodo`) VALUES
(123123123, 'theo kabir novais de carvalho', '30/03/2004', '52802015888', 'theogame4@gmail.com', 'av taruma 505', 'São Paulo', 'SP', '11933162008', 'ciencia', 'noturno'),
(11111111, '111111111', '30/03/2004', '11111111111', 'sdfasdfasdfasdfasdfa', 'sdfasdfasdf', 'asdfasdfasdfasdf', 'AC', '11111111111', 'asdfasdfasdfasdfdas', 'noturno');

-- --------------------------------------------------------

--
-- Estrutura da tabela `curso`
--

DROP TABLE IF EXISTS `curso`;
CREATE TABLE IF NOT EXISTS `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `curso`
--

INSERT INTO `curso` (`id`, `nome`) VALUES
(1, 'Ciência da computação'),
(2, 'Administração'),
(3, 'Astronomia'),
(4, 'Engenharia de software'),
(5, 'Engenharia de hadrware'),
(6, 'História'),
(7, 'Geografia'),
(8, 'Relações internacionais'),
(9, 'Análise e desenvolvimento de sistemas');

-- --------------------------------------------------------

--
-- Estrutura da tabela `nota`
--

DROP TABLE IF EXISTS `nota`;
CREATE TABLE IF NOT EXISTS `nota` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_professor` int DEFAULT NULL,
  `rgm_aluno` int DEFAULT NULL,
  `semestre` varchar(6) DEFAULT NULL,
  `falta` int DEFAULT '0',
  `nota` int DEFAULT NULL,
  `peso` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_professor` (`id_professor`),
  KEY `rgm_aluno` (`rgm_aluno`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `nota`
--

INSERT INTO `nota` (`id`, `id_professor`, `rgm_aluno`, `semestre`, `falta`, `nota`, `peso`) VALUES
(2, 1, 123123123, '2023-1', 111, 111, 11);

-- --------------------------------------------------------

--
-- Estrutura da tabela `professor`
--

DROP TABLE IF EXISTS `professor`;
CREATE TABLE IF NOT EXISTS `professor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

--
-- Extraindo dados da tabela `professor`
--

INSERT INTO `professor` (`id`, `name`, `password`, `subject`) VALUES
(1, 'theo', '123456789', 'poo');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
