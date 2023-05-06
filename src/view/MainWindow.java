package view;

import dao.AlunoDao;
import dao.NotaDao;
import model.Aluno;
import model.Nota;
import pojo.Curso;
import pojo.Disciplina;
import utils.Messages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.Calendar;

public class MainWindow extends JFrame {

	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JTextField rgmTextAluno;
	private JFormattedTextField nascimentoTextAluno;
	private JFormattedTextField cpfTextAluno;
	private JTextField emailTextAluno;
	private JTextField enderecoTextAluno;
	private JTextField municipioTextAluno;
	private JFormattedTextField celularTextAluno;
	private JTextField rgmTextNotas;
	private JTextField nomeTextNotas;
	private JTextField cursoTextNotas;
	private JTextField faltasTextNotas;
	private JComboBox ufBox;
	private JRadioButton noturnoRd;
	private JRadioButton vespertinoRd;
	private JRadioButton matutinoRd;
	private JComboBox campusBox;
	private JComboBox<String> disciplinaBox;
	private JComboBox<String> notasCombo;
	private JComboBox cursoBox;
	private JComboBox<String> semestreBox;
	private ButtonGroup periodoGroup;
	private AlunoDao alunoDao = new AlunoDao();
	private NotaDao notaDao = new NotaDao();
	private JTextField nomeTextAluno;
	private JTextField idNotasText;
	private JComboBox<String> semestreBoxBoletim;
	private JLabel nomeBoletim;
	private JLabel cursoBoletim;
	private JTextField rgmBoletimTxt;
	private JList<Disciplina> disciplinasList;
	private JLabel faltasTotal;
	private JList<Nota> notasList;
	private JLabel mediaDisciplinaText;
	private JLabel faltaDisciplinaText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		MaskFormatter dataFormater = null;
		MaskFormatter cpfFormater = null;
		MaskFormatter celularFormater = null;

		try{
			dataFormater = new MaskFormatter("##/##/####");
			cpfFormater = new MaskFormatter("###.###.###-##");
			celularFormater = new MaskFormatter("(##) #####-####");
		}catch (Exception e){
			System.exit(-1);
		}

		ImageIcon iconSalvar = new ImageIcon(getClass().getResource("/images/save-icon.png"));
		ImageIcon iconConsultar = new ImageIcon(getClass().getResource("/images/search-icon.png"));
		ImageIcon iconAlterar = new ImageIcon(getClass().getResource("/images/edit-icon.png"));
		ImageIcon iconDeletar = new ImageIcon(getClass().getResource("/images/delete-icon.png"));
		ImageIcon iconClear = new ImageIcon(getClass().getResource("/images/clear-icon.png"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 608, 22);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Aluno");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Salvar");
		mntmNewMenuItem_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_5.addActionListener(this::salvarAluno);
		mnNewMenu.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Alterar");
		mntmNewMenuItem_6.addActionListener(this::alterarAluno);
		mnNewMenu.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Consultar");
		mntmNewMenuItem_7.addActionListener(this::consultarAluno);
		mnNewMenu.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Excluir");
		mntmNewMenuItem_8.addActionListener(this::excluirAluno);
		mnNewMenu.add(mntmNewMenuItem_8);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Sair");
		mntmNewMenuItem_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK));
		mntmNewMenuItem_9.addActionListener(this::sair);
		mnNewMenu.add(mntmNewMenuItem_9);
		
		JMenu mnNewMenu_1 = new JMenu("Notas e Faltas");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("salvar");
		mntmNewMenuItem_1.addActionListener(this::salvarNota);
		mntmNewMenuItem_1.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Alterar");
		mntmNewMenuItem_2.addActionListener(this::alterarNota);
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Excluir");
		mntmNewMenuItem_3.addActionListener(this::excluirNota);
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Consultar");
		mntmNewMenuItem_4.addActionListener(this::consultarNota);
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_2 = new JMenu("Ajuda");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("sobre");
		mntmNewMenuItem.addActionListener(this::sobre);
		mnNewMenu_2.add(mntmNewMenuItem);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 33, 588, 360);
		contentPane.add(tabbedPane);
		
		JPanel alunoPanel = new JPanel();
		tabbedPane.addTab("Aluno", null, alunoPanel, null);
		alunoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("RGM");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 45, 27);
		alunoPanel.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBounds(235, 11, 80, 27);
		alunoPanel.add(lblNome);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataDeNascimento.setBounds(10, 60, 143, 27);
		alunoPanel.add(lblDataDeNascimento);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBounds(310, 60, 80, 27);
		alunoPanel.add(lblCpf);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmail.setBounds(10, 110, 55, 27);
		alunoPanel.add(lblEmail);
		
		JLabel lblEnd = new JLabel("End.");
		lblEnd.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEnd.setBounds(10, 151, 55, 27);
		alunoPanel.add(lblEnd);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMunicipio.setBounds(10, 198, 72, 27);
		alunoPanel.add(lblMunicipio);
		
		JLabel lblUf = new JLabel("UF");
		lblUf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUf.setBounds(197, 198, 21, 27);
		alunoPanel.add(lblUf);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCelular.setBounds(310, 198, 72, 27);
		alunoPanel.add(lblCelular);
		
		rgmTextAluno = new JTextField();
		rgmTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		rgmTextAluno.setBounds(50, 11, 175, 27);
		alunoPanel.add(rgmTextAluno);
		rgmTextAluno.setColumns(10);
		
		nascimentoTextAluno = new JFormattedTextField(dataFormater);
		nascimentoTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		nascimentoTextAluno.setColumns(10);
		nascimentoTextAluno.setBounds(163, 60, 143, 27);
		alunoPanel.add(nascimentoTextAluno);
		
		cpfTextAluno = new JFormattedTextField(cpfFormater);
		cpfTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		cpfTextAluno.setColumns(10);
		cpfTextAluno.setBounds(349, 60, 224, 27);
		alunoPanel.add(cpfTextAluno);
		
		emailTextAluno = new JTextField();
		emailTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		emailTextAluno.setColumns(10);
		emailTextAluno.setBounds(57, 110, 516, 27);
		alunoPanel.add(emailTextAluno);
		
		enderecoTextAluno = new JTextField();
		enderecoTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		enderecoTextAluno.setColumns(10);
		enderecoTextAluno.setBounds(57, 148, 516, 27);
		alunoPanel.add(enderecoTextAluno);
		
		municipioTextAluno = new JTextField();
		municipioTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		municipioTextAluno.setColumns(10);
		municipioTextAluno.setBounds(79, 198, 108, 27);
		alunoPanel.add(municipioTextAluno);
		
		celularTextAluno = new JFormattedTextField(celularFormater);
		celularTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		celularTextAluno.setColumns(10);
		celularTextAluno.setBounds(366, 198, 207, 27);
		alunoPanel.add(celularTextAluno);
		
		ufBox = new JComboBox<>();
		ufBox.setModel(new DefaultComboBoxModel<>(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MS", "MT", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		ufBox.setFont(new Font("Arial", Font.PLAIN, 16));
		ufBox.setBounds(220, 198, 80, 27);
		alunoPanel.add(ufBox);
		
		nomeTextAluno = new JTextField();
		nomeTextAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		nomeTextAluno.setColumns(10);
		nomeTextAluno.setBounds(287, 11, 286, 27);
		alunoPanel.add(nomeTextAluno);
		
		JPanel cursoPanel = new JPanel();
		tabbedPane.addTab("Curso", null, cursoPanel, null);
		cursoPanel.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Curso");
		lblNewLabel_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_2.setBounds(10, 11, 69, 27);
		cursoPanel.add(lblNewLabel_1_1_2);
		
		cursoBox = new JComboBox<>();
		cursoBox.setModel(new DefaultComboBoxModel<>(Curso.values()));
		cursoBox.setFont(new Font("Arial", Font.PLAIN, 16));
		cursoBox.setBounds(89, 11, 484, 27);
		cursoPanel.add(cursoBox);
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Campus");
		lblNewLabel_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_2_1.setBounds(10, 49, 69, 27);
		cursoPanel.add(lblNewLabel_1_1_2_1);
		
		campusBox = new JComboBox<>();
		campusBox.setModel(new DefaultComboBoxModel<>(new String[] {"Insira Campus", "Tatuapé", "Pinheiros"}));
		campusBox.setFont(new Font("Arial", Font.PLAIN, 16));
		campusBox.setBounds(89, 49, 484, 27);
		cursoPanel.add(campusBox);
		
		matutinoRd = new JRadioButton("Matutino");
		matutinoRd.setBounds(89, 91, 109, 23);
		cursoPanel.add(matutinoRd);
		
		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Periodo");
		lblNewLabel_1_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_2_1_1.setBounds(10, 87, 69, 27);
		cursoPanel.add(lblNewLabel_1_1_2_1_1);
		
		vespertinoRd = new JRadioButton("Vespertino");
		vespertinoRd.setBounds(200, 91, 109, 23);
		cursoPanel.add(vespertinoRd);

		noturnoRd = new JRadioButton("Noturno");
		noturnoRd.setSelected(true);
		noturnoRd.setBounds(323, 91, 109, 23);
		cursoPanel.add(noturnoRd);

		periodoGroup = new ButtonGroup();
		periodoGroup.add(matutinoRd);
		periodoGroup.add(vespertinoRd);
		periodoGroup.add(noturnoRd);

		JButton salvarCursoBtn = new JButton();
		salvarCursoBtn.setIcon(iconSalvar);
		salvarCursoBtn.setToolTipText("salvar curso");
		salvarCursoBtn.addActionListener(this::salvarCurso);
		salvarCursoBtn.setBounds(89, 146, 69, 62);
		cursoPanel.add(salvarCursoBtn);
		
		JButton consultarCursoBtn = new JButton();
		consultarCursoBtn.setIcon(iconConsultar);
		consultarCursoBtn.setToolTipText("Consultar curso");
		consultarCursoBtn.addActionListener(this::consultarAluno);
		consultarCursoBtn.setBounds(168, 146, 69, 62);
		cursoPanel.add(consultarCursoBtn);
		
		JButton alterarCursoBtn = new JButton();
		alterarCursoBtn.setToolTipText("Alterar curso");
		alterarCursoBtn.setIcon(iconAlterar);
		// utiliza do mesmo valor pois o campo de curso é, por padrão, nulo no registro do banco de dados, bastando apenas a alteração para salvar e para alterar
		alterarCursoBtn.addActionListener(this::salvarCurso);
		alterarCursoBtn.setBounds(247, 146, 69, 62);
		cursoPanel.add(alterarCursoBtn);
		
		JButton deletarCursoBtn = new JButton();
		deletarCursoBtn.setIcon(iconDeletar);
		deletarCursoBtn.setToolTipText("Deletar curso");
		deletarCursoBtn.addActionListener(this::excluirCurso);
		deletarCursoBtn.setBounds(326, 146, 69, 62);
		cursoPanel.add(deletarCursoBtn);
		
		JButton limparCursoBtn = new JButton();
		limparCursoBtn.setIcon(iconClear);
		limparCursoBtn.setToolTipText("Limpar curso");
		limparCursoBtn.addActionListener(this::limparCurso);
		limparCursoBtn.setBounds(405, 146, 69, 61);
		cursoPanel.add(limparCursoBtn);

		
		JPanel notasPanel = new JPanel();
		tabbedPane.addTab("Notas e Faltas", null, notasPanel, null);
		notasPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("RGM");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 48, 45, 27);
		notasPanel.add(lblNewLabel_1);
		
		rgmTextNotas = new JTextField();
		rgmTextNotas.setFont(new Font("Arial", Font.PLAIN, 16));
		rgmTextNotas.setColumns(10);
		rgmTextNotas.setBounds(57, 48, 168, 27);
		notasPanel.add(rgmTextNotas);
		
		nomeTextNotas = new JTextField();
		nomeTextNotas.setText("NOME DO ALUNO");
		nomeTextNotas.setFont(new Font("Arial", Font.PLAIN, 16));
		nomeTextNotas.setEditable(false);
		nomeTextNotas.setColumns(10);
		nomeTextNotas.setBounds(235, 48, 338, 27);
		notasPanel.add(nomeTextNotas);
		
		cursoTextNotas = new JTextField();
		cursoTextNotas.setText("CURSO DO ALUNO");
		cursoTextNotas.setFont(new Font("Arial", Font.PLAIN, 16));
		cursoTextNotas.setEditable(false);
		cursoTextNotas.setColumns(10);
		cursoTextNotas.setBounds(10, 86, 563, 27);
		notasPanel.add(cursoTextNotas);
		
		JLabel lblNewLabel_1_1 = new JLabel("Disciplina");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(10, 124, 69, 27);
		notasPanel.add(lblNewLabel_1_1);
		
		disciplinaBox = new JComboBox();
		disciplinaBox.setFont(new Font("Arial", Font.PLAIN, 16));
		disciplinaBox.setBounds(89, 124, 484, 27);
		notasPanel.add(disciplinaBox);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Semestre");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1.setBounds(10, 162, 69, 27);
		notasPanel.add(lblNewLabel_1_1_1);

		semestreBox = new JComboBox<>();
		semestreBox.setModel(new DefaultComboBoxModel<>(getSemestres()));
		semestreBox.setFont(new Font("Arial", Font.PLAIN, 16));
		semestreBox.setBounds(89, 162, 93, 27);
		notasPanel.add(semestreBox);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Nota");
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setBounds(217, 162, 39, 27);
		notasPanel.add(lblNewLabel_1_1_1_1);
		
		notasCombo = new JComboBox<>();
		notasCombo.setModel(new DefaultComboBoxModel<>(new String[] {"0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10"}));
		notasCombo.setFont(new Font("Arial", Font.PLAIN, 16));
		notasCombo.setBounds(266, 162, 93, 27);
		notasPanel.add(notasCombo);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Faltas");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1_1.setBounds(381, 162, 54, 27);
		notasPanel.add(lblNewLabel_1_1_1_1_1);
		
		faltasTextNotas = new JTextField();
		faltasTextNotas.setFont(new Font("Arial", Font.PLAIN, 16));
		faltasTextNotas.setColumns(10);
		faltasTextNotas.setBounds(445, 162, 109, 27);
		notasPanel.add(faltasTextNotas);
		
		JButton salvarNotaBtn = new JButton();
		salvarNotaBtn.setIcon(iconSalvar);
		salvarNotaBtn.setToolTipText("Salvar nota");
		salvarNotaBtn.addActionListener(this::salvarNota);
		salvarNotaBtn.setBounds(94, 212, 69, 62);
		notasPanel.add(salvarNotaBtn);

		JButton consultarNotaBtn = new JButton();
		consultarNotaBtn.setIcon(iconConsultar);
		consultarNotaBtn.setToolTipText("Consultar nota");
		consultarNotaBtn.addActionListener(this::consultarNota);
		consultarNotaBtn.setBounds(173, 212, 69, 62);
		notasPanel.add(consultarNotaBtn);

		JButton alterarNotaBtn = new JButton();
		alterarNotaBtn.setIcon(iconAlterar);
		alterarNotaBtn.setToolTipText("Alterar nota");
		alterarNotaBtn.addActionListener(this::alterarNota);
		alterarNotaBtn.setBounds(252, 212, 69, 62);
		notasPanel.add(alterarNotaBtn);

		JButton deletarNotaBtn = new JButton();
		deletarNotaBtn.setIcon(iconDeletar);
		deletarNotaBtn.setToolTipText("Deletar nota");
		deletarNotaBtn.addActionListener(this::excluirNota);
		deletarNotaBtn.setBounds(331, 211, 69, 63);
		notasPanel.add(deletarNotaBtn);

		JButton limparNotaBtn = new JButton();
		limparNotaBtn.setIcon(iconClear);
		limparNotaBtn.setToolTipText("Limpar nota");
		limparNotaBtn.addActionListener(this::limparNota);
		limparNotaBtn.setBounds(410, 212, 69, 62);
		notasPanel.add(limparNotaBtn);
		
		JLabel lblNewLabel_1_2 = new JLabel("ID");
		lblNewLabel_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(10, 11, 45, 27);
		notasPanel.add(lblNewLabel_1_2);
		
		idNotasText = new JTextField();
		idNotasText.setFont(new Font("Arial", Font.PLAIN, 16));
		idNotasText.setColumns(10);
		idNotasText.setBounds(57, 11, 93, 27);
		notasPanel.add(idNotasText);

		JPanel boletimPanel = new JPanel();
		tabbedPane.addTab("Boletim", null, boletimPanel, null);
		boletimPanel.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Boletim");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_2_1.setBounds(10, 11, 563, 27);
		boletimPanel.add(lblNewLabel_1_2_1);
		
		JButton gerarBoletimBtn = new JButton("Gerar boletim");
		gerarBoletimBtn.addActionListener(this::gerarBoletim);
		gerarBoletimBtn.setBounds(358, 113, 215, 23);
		boletimPanel.add(gerarBoletimBtn);
		
		nomeBoletim = new JLabel("RGM - NOME");
		nomeBoletim.setBounds(10, 49, 416, 14);
		boletimPanel.add(nomeBoletim);
		
		cursoBoletim = new JLabel("CURSO - PERIODO - CAMPUS");
		cursoBoletim.setBounds(10, 68, 342, 14);
		boletimPanel.add(cursoBoletim);
		
		semestreBoxBoletim = new JComboBox<>();
		semestreBoxBoletim.setModel(new DefaultComboBoxModel<>(getSemestres()));
		semestreBoxBoletim.setFont(new Font("Arial", Font.PLAIN, 16));
		semestreBoxBoletim.setBounds(437, 75, 93, 27);
		boletimPanel.add(semestreBoxBoletim);
		
		JButton btnChecarDisciplina = new JButton("checar disciplina");
		btnChecarDisciplina.addActionListener(this::checarDisciplina);
		btnChecarDisciplina.setBounds(358, 172, 215, 23);
		boletimPanel.add(btnChecarDisciplina);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Faltas:");
		lblNewLabel_2_1_1.setBounds(358, 147, 38, 14);
		boletimPanel.add(lblNewLabel_2_1_1);
		
		faltasTotal = new JLabel("<>");
		faltasTotal.setBounds(406, 147, 50, 14);
		boletimPanel.add(faltasTotal);
		
		JLabel lblNewLabel_2_1_1_2 = new JLabel("Média na disciplina: ");
		lblNewLabel_2_1_1_2.setBounds(358, 223, 133, 14);
		boletimPanel.add(lblNewLabel_2_1_1_2);
		
		JLabel lblNewLabel_2_1_1_2_1 = new JLabel("Faltas na disciplina: ");
		lblNewLabel_2_1_1_2_1.setBounds(358, 248, 133, 14);
		boletimPanel.add(lblNewLabel_2_1_1_2_1);
		
		mediaDisciplinaText = new JLabel("<>");
		mediaDisciplinaText.setBounds(501, 223, 50, 14);
		boletimPanel.add(mediaDisciplinaText);
		
		faltaDisciplinaText = new JLabel("<>");
		faltaDisciplinaText.setBounds(501, 248, 50, 14);
		boletimPanel.add(faltaDisciplinaText);
		
		JLabel lblNewLabel_1_3 = new JLabel("RGM");
		lblNewLabel_1_3.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_3.setBounds(358, 36, 45, 27);
		boletimPanel.add(lblNewLabel_1_3);
		
		rgmBoletimTxt = new JTextField();
		rgmBoletimTxt.setFont(new Font("Arial", Font.PLAIN, 16));
		rgmBoletimTxt.setColumns(10);
		rgmBoletimTxt.setBounds(436, 36, 147, 27);
		boletimPanel.add(rgmBoletimTxt);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Semestre");
		lblNewLabel_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1_2.setBounds(358, 75, 69, 27);
		boletimPanel.add(lblNewLabel_1_1_1_2);

		disciplinasList = new JList<>();
		disciplinasList.setBounds(10, 93, 342, 106);
		boletimPanel.add(disciplinasList);
		
		notasList = new JList<>();
		notasList.setBounds(10, 210, 342, 111);
		boletimPanel.add(notasList);
		
		JButton alterarBtn = new JButton("alterar nota");
		alterarBtn.addActionListener(this::redirecionarNota);
		alterarBtn.setBounds(358, 298, 127, 23);
		boletimPanel.add(alterarBtn);
	}

	/**
	 * Função que encerra o programa
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void sair(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "saindo");
		System.exit(0);
	}

	/**
	 * função para facilitar a transformação dos campos do formulário em um objeto do tipo {@link Aluno}
	 * @return retorna o bjeto Aluno criado a partir dos campos do formulário
	 */
	public Aluno pegarAluno(){
		String cpf = cpfTextAluno.getText();
		String celular = celularTextAluno.getText();
		String nascimento = nascimentoTextAluno.getText();

		return new Aluno(
				Integer.parseInt(rgmTextAluno.getText()),
				nomeTextAluno.getText(),
				nascimento,
				cpf,
				emailTextAluno.getText(),
				enderecoTextAluno.getText(),
				municipioTextAluno.getText(),
				ufBox.getSelectedItem().toString(),
				celular
		);
	}

	/**
	 * Função leva os dados do formulário para a função {@link AlunoDao#cadastrar(Aluno)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void salvarAluno(ActionEvent e){
		Aluno aluno = pegarAluno();
		alunoDao.cadastrar(aluno);
	}

	/**
	 * Função leva os dados do formulário para a função {@link AlunoDao#alterar(Aluno)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void alterarAluno(ActionEvent e){
		Aluno aluno = pegarAluno();
		alunoDao.alterar(aluno);
	}

	/**
	 * Função que utiliza a função {@link AlunoDao#checarAluno(int)} para inserir os dados do aluno nos campos de formulário para que o usuário o verifique e possa fazer as alterações corretas
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void consultarAluno(ActionEvent e){
		int rgm;
		try{
			rgm = Integer.parseInt(
					(rgmTextAluno.getText().isBlank()) ? rgmTextNotas.getText() : rgmTextAluno.getText()
			);
		}catch(NumberFormatException err){
			Messages.popup("erro ao informar numero");
			return;
		}
		Aluno aluno = alunoDao.checarAluno(rgm);
		if(aluno == null){
			Messages.popup("aluno não encontrado");
			return;
		}
		rgmTextAluno.setText(String.valueOf(aluno.getRgm()));
		rgmTextNotas.setText(String.valueOf(aluno.getRgm()));
		rgmBoletimTxt.setText(String.valueOf(aluno.getRgm()));
		nomeTextAluno.setText(aluno.getNome());
		nomeTextNotas.setText(aluno.getNome());
		nascimentoTextAluno.setText(aluno.getNascimento());
		cpfTextAluno.setText(aluno.getCpf());
		emailTextAluno.setText(aluno.getEmail());
		enderecoTextAluno.setText(aluno.getEndereco());
		municipioTextAluno.setText(aluno.getMunicipio());
		ufBox.setSelectedItem(aluno.getUf());
		celularTextAluno.setText(aluno.getCelular());
		cursoTextNotas.setText(aluno.getCurso());

		cursoBox.setSelectedItem((aluno.getCurso()==null)?Curso.NULO:Curso.get(aluno.getCurso()));
		campusBox.setSelectedItem((aluno.getCampus()==null)?"Insira Campus":aluno.getCampus());



		inserirPeriodo(aluno);

		if(!aluno.getCurso().isBlank())
			atualizarDisciplina();
	}

	/**
	 * Função que deleta o aluno apartir do rgm informado no formulário e envia para a função {@link AlunoDao#excluir(int)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void excluirAluno(ActionEvent e){
		alunoDao.excluir(Integer.parseInt(rgmTextAluno.getText()));
		zerarDisciplina();
	}

	/**
	 * Função que verifica os botões de periodo e retorna o valor selecionado
	 * @return retorna a String referente ao valor selecionado
	 */
	public String getPeriodo(){
		if(matutinoRd.isSelected()) return "Matutino";
		else if(vespertinoRd.isSelected()) return "Vespertino";
		else if (noturnoRd.isSelected()) return "Noturno";
		else return null;
	}

	/**
	 * Função para facilitar a inserção do valor de periodo recebido pelo banco de dados
	 * @param aluno objeto {@link Aluno} cujo o periodo será inserido no formulário
	 */
	public void inserirPeriodo(Aluno aluno){
		if(aluno.getPeriodo()!=null)
			switch (aluno.getPeriodo()){
				case "Vespertino"->vespertinoRd.setSelected(true);
				case "Noturno"->noturnoRd.setSelected(true);

				// o valor defalut é o matutino

				default ->matutinoRd.setSelected(true);
			}
		else
			matutinoRd.setSelected(true);
	}

	/**
	 * Função que verifica se os campos curso e campus tem valores válidos para serem inseridos no banco
	 * @return retorna um valor booleano usado na verificação de validade dos campos
	 */
	public boolean verificarCampos(){
		Curso curso = (Curso) cursoBox.getSelectedItem();
		String campus = (String) campusBox.getSelectedItem();

		if(curso.equals(Curso.NULO)){
			Messages.popup("Insira um curso");
			return false;
		}

		if(campus.equals("Insira Campus")){
			Messages.popup("Insira um campus");
			return false;
		}

		return true;
	}

	/**
	 * função que pega os valores de curso do formulário e os insere no banco com base no rgm do aluno da aba Aluno
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void salvarCurso(ActionEvent e){

		if(!verificarCampos()) return;

		String rgm = rgmTextAluno.getText();
		String periodo = getPeriodo();
		if(rgm.isBlank()){
			Messages.popup("Rgm não informado na aba de aluno");
			return;
		}
		if(periodo.isBlank()){
			Messages.popup("erro na seleção de periodo");
			return;
		}

		Aluno aluno = new Aluno(
				Integer.parseInt(rgm),
				cursoBox.getSelectedItem().toString(),
				campusBox.getSelectedItem().toString(),
				periodo
		);

		alunoDao.trocarCurso(aluno);
		rgmTextNotas.setText(rgm);
		cursoTextNotas.setText(cursoBox.getSelectedItem().toString());
		atualizarDisciplina();
	}

	/**
	 * Função que exclui os campos referentes ao curso do aluno a partir da fuinção {@link AlunoDao#excluirCurso(int)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void excluirCurso(ActionEvent e){
		try{
			int rgm = Integer.parseInt(rgmTextAluno.getText());

			boolean confirm = Messages.confirm("Deseja mesmo remover o aluno desde curso?");

			if(confirm)
				alunoDao.excluirCurso(rgm);

		}catch (NumberFormatException err){
			Messages.error("erro ao informar rgm na página de aluno");
		}

	}

	/**
	 * função que limpa os dados do formulário de curso
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void limparCurso(ActionEvent e){
		matutinoRd.setSelected(true);
		cursoBox.setSelectedIndex(0);
		campusBox.setSelectedIndex(0);
	}

	/**
	 * função feita para facilitar a atualização do comboBox de disciplinas da aba de Notas
	 */
	public void atualizarDisciplina(){
		if(cursoTextNotas.getText().isBlank())
			return;
		Curso curso = Curso.get(cursoTextNotas.getText());

		disciplinaBox.setModel(new DefaultComboBoxModel<>(curso.disciplinas));
	}

	/**
	 * Função feita para retirar os valores de dentro da comboBox de disciplinas da aba de notas
	 */
	public void zerarDisciplina(){
		disciplinaBox.setModel(new DefaultComboBoxModel<>());
	}

	/**
	 * Função simples feita para verificar a existencia de dados no campo do RGM
	 * @return boolean sobre a validade do campo
	 */
	public boolean rgmVazio(){
		String rgm = rgmTextNotas.getText();

		return rgm.isBlank();
	}

	/**
	 * Função utilizada para facilitar a criação de um objeto {@link Nota} com base nos dados do formulário
	 * @return retorna o objeto {@link Nota}
	 */
	public Nota pegarNota(){

		int rgm = Integer.parseInt(rgmTextNotas.getText());
		String disciplina = disciplinaBox.getSelectedItem().toString();
		String semestre = semestreBox.getSelectedItem().toString();
		float nota = Float.parseFloat(notasCombo.getSelectedItem().toString());
		int faltas = (faltasTextNotas.getText().isBlank())?0:Integer.parseInt(faltasTextNotas.getText());
		int id = Integer.parseInt(idNotasText.getText().isBlank()?"0":idNotasText.getText()	);

		return new Nota(id, rgm, disciplina, semestre, nota, faltas);
	}

	/**
	 * Função feita para salvar a nota no banco de dados atravez do método {@link NotaDao#adicionar(Nota)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void salvarNota(ActionEvent e){
		if(rgmVazio()){
			Messages.popup("preencher rgm");
			return;
		}
		if(disciplinaBox.getItemCount() == 0){
			Messages.popup("Consulte um aluno com curso registrado para selecionar a disciplina");
			return;
		}

		Nota nota = pegarNota();
		notaDao.adicionar(nota);
	}

	/**
	 * Função que junta os dados do formulário para fazer a alteração da nota atravéz do método {@link NotaDao#alterar(Nota)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void alterarNota(ActionEvent e){

		boolean confirm = Messages.confirm(
				String.format("Deseja alterar a nota %s?\n" +
						"ATENÇÂO: a nota deve pertencer ao aluno do rgm parta ser alterada",
						idNotasText.getText())
		);

		if(confirm){
			Nota nota = pegarNota();
			notaDao.alterar(nota);
		}

	}

	/**
	 * Função que pega o id da nota e consulta atravéz do método {@link NotaDao#consultar(int)} e insere os dados no formuilário
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void consultarNota(ActionEvent e){

		int id = Integer.parseInt(idNotasText.getText());
		Nota nota = notaDao.consultar(id);

		if(nota == null){
			Messages.popup("nota não encontrada");
			return;
		}

		rgmTextAluno.setText(String.valueOf(nota.getRgm_aluno()));
		consultarAluno(null);

		disciplinaBox.setSelectedItem(nota.getDisciplina());
		faltasTextNotas.setText(String.valueOf(nota.getFalta()));
		notasCombo.setSelectedItem(Float.toString(nota.getNota()).replace(".0", ""));
	}

	/**
	 * Função que faz a exclusão da nota a partir do id escrito no formulário e utilizando o método {@link NotaDao#excluir(int)}
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void excluirNota(ActionEvent e){

		if(!Messages.confirm("Deseja excluir a nota?")) return;

		int id = Integer.parseInt(idNotasText.getText());
		notaDao.excluir(id);

		limparNota(null);
	}

	/**
	 * Função que limpa todas as informações do formulário de notas
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void limparNota(ActionEvent e){
		disciplinaBox.setSelectedIndex(0);
		faltasTextNotas.setText("0");
		notasCombo.setSelectedIndex(0);
		semestreBox.setSelectedIndex(0);
		rgmTextAluno.setText("");
		idNotasText.setText("");
	}

	/**
	 * Função que utiliza do rgm e do semestre informados na aba de boletim para gerar as informações dentro de um JList
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void gerarBoletim(ActionEvent e){

		int rgm = Integer.parseInt(rgmBoletimTxt.getText());
		String semestre = (String) semestreBoxBoletim.getSelectedItem();

		Aluno aluno = alunoDao.checarAluno(rgm);
		if(aluno == null) {
			Messages.popup("aluno não encontrado");
			return;
		}

		if(aluno.getCurso() == null){
			Messages.popup("Aluno não está cadastrado em nenhum curso");
		}

		nomeBoletim.setText(String.format("%d - %s", aluno.getRgm(), aluno.getNome()));
		cursoBoletim.setText(String.format("%s - %s - %s", aluno.getCurso(), aluno.getPeriodo(), aluno.getCampus()));

		Disciplina[] disciplinas = notaDao.gerarBoletimBase(rgm, semestre);

		System.out.println(disciplinas);

		/*
		Para garantir que as disciplinas sejam as disciplinas referentes ao
		curso do aluno é feito uma filtragem transformando o Array em um stream
		e utilizando do método filter que retorna apenas as disciplinas com o campo
		disciplina que se refere corretamente ao curso do aluno
		 */
		disciplinas = Arrays.stream(disciplinas)
				.filter(d ->
						Curso.get(aluno.getCurso()).checarDisciplina(d.getDisciplina()))
				.toArray(Disciplina[]::new);

		int somaFaltas = 0;
		for(Disciplina d: disciplinas)
			somaFaltas += d.getFaltas();

		faltasTotal.setText(String.valueOf(somaFaltas));

		disciplinasList.setModel(new DefaultComboBoxModel<>(disciplinas));

	}

	/**
	 *	Utiliza da disciplina selecionada no JList do boletim para resgatar todas as notas referentes a
	 *	ela e informar no JList abaixo, onde mostre seu id para facilitar a edição
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void checarDisciplina(ActionEvent e){
		Disciplina d = disciplinasList.getSelectedValue();

		int rgm = Integer.parseInt(rgmBoletimTxt.getText());
		String semestre = (String) semestreBoxBoletim.getSelectedItem();
		String disciplina = d.getDisciplina();

		Nota[] notas = notaDao.getNotasBoletim(rgm, semestre, disciplina);

		int qtd_notas = 0;
		float media = 0f;
		int faltas = 0;

		for(Nota n: notas){
			media += n.getNota();
			qtd_notas++;
			faltas += n.getFalta();
		}

		media /= qtd_notas;

		mediaDisciplinaText.setText(String.format("%.2f", media));
		faltaDisciplinaText.setText(String.valueOf(faltas));

		notasList.setModel(new DefaultComboBoxModel<>(notas));

	}

	/**
	 * Função que utiliza da nota selecionada no objeto notasList para preencher o formulário da aba de notas e redirecionar o usuário para ela com os campos todos preenchidos para facilitar sua edição
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void redirecionarNota(ActionEvent e){

		Nota selecionado = notasList.getSelectedValue();
		idNotasText.setText(String.valueOf(selecionado.getId()));
		consultarNota(null);

		tabbedPane.setSelectedIndex(2);
	}

	/**
	 * Mostra a tela sobre, que informa sobre a existência do projeto
	 * @param e objeto {@link ActionEvent} enviado pelo elemento que ativa a função
	 */
	public void sobre (ActionEvent e){
		Sobre.aboutScreen();
	}

	/**
	 * Função para gerar um Array de String com os semestres disponíveis para inserção de nota e geração dos boletins
	 */
	public String[] getSemestres(){
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH) + 1;
		int size = 12;

		int semestre = (int) Math.ceil(mes/6.0);
		String[] semestres = new String[size];
		for(int i = 0; i < size; i++){
			semestres[i] = String.format("%d-%d", ano, semestre);
			if(semestre == 2) semestre = 1;
			else{
				semestre = 2;
				ano--;
			}
		}

		return semestres;
	}
}
