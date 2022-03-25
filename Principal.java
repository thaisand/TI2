public class Principal {
	
	public static void main(String[] args) {
		
		DAO dao = new DAO();
		
		dao.conectar();

		
		//Inserir um elemento na tabela
		Aluno aluno = new Aluno(11, "pablo", "pablo",'M');
		if(dao.inserirAluno(aluno) == true) {
			System.out.println("Inserção com sucesso -> " + aluno.toString());
		}
		
		//Mostrar usuários do sexo masculino		
		System.out.println("==== Mostrar usuários do sexo masculino === ");
		Aluno[] alunos = dao.getAlunosMasculinos();
		for(int i = 0; i < alunos.length; i++) {
			System.out.println(alunos[i].toString());
		}

		//Atualizar usuário
		aluno.setSenha("nova senha");
		dao.atualizarAluno(aluno);

		//Mostrar usuários do sexo masculino
		System.out.println("==== Mostrar usuários === ");
		alunos = dao.getAlunos();
		for(int i = 0; i < alunos.length; i++) {
			System.out.println(alunos[i].toString());
		}
		
		//Excluir usuário
		dao.excluirAluno(aluno.getCodigo());
		
		//Mostrar usuários
		alunos = dao.getAlunos();
		System.out.println("==== Mostrar usuários === ");		
		for(int i = 0; i < alunos.length; i++) {
			System.out.println(alunos[i].toString());
		}
		
		dao.close();
	}
}