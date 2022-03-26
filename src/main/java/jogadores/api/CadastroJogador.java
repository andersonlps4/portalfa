package jogadores.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

public class CadastroJogador {

	public static void main(String[] args) {
		Javalin app = Javalin.create();
		app.start(7070);
		app.post("/teste", ctx -> {
			boolean result = addJogador(ctx.queryParam("name"), ctx.queryParam("bday"), ctx.queryParam("email"),
					ctx.queryParam("phone"), ctx.queryParam("clubs"), ctx.queryParam("videoUrl"),
					ctx.queryParam("sports"), ctx.queryParam("city"), ctx.queryParam("state"));
			if (result) {
				ctx.status(201);
			} else {
				ctx.status(500);
			}
		});
		// app.get("/aluno/{id}", ctx -> ctx.result(addJogador(ctx.pathParam("id"))));

	}

	public static boolean addJogador(String name, String bday, String email, String phone, String clubs,
			String videoUrl, String sports, String city, String state) throws SQLException {
		Dotenv dotenv = Dotenv.load();
		Connection conexao = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexao = DriverManager.getConnection("jdbc:postgresql://" + dotenv.get("HOST") + dotenv.get("DATABASE"),
					dotenv.get("USER"), dotenv.get("PASSWORD"));
			String sqlInsert = "insert into jogadores ";
			String sqlColumns = "(nome, data_nascimento, email,telefone,ja_jogou,possui_video_jogando, pratica_esporte,cidade, estado)";
			String sqlValues = "values (" + name +","+ bday +","+ email +","+ phone +","+ clubs +","+ videoUrl +","+ sports +","+ city +","+ state
					+ ")";
			String sqlQuery = sqlInsert + sqlColumns + sqlValues;
			ResultSet aluno = conexao.createStatement().executeQuery(sqlQuery);
			if (conexao != null)
				conexao.close();
			else
				return false;
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			return false;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

}
