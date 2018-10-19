/* 
	Feito para aula em canal do Youtube
	Por Danilo Marcus
	Acesse o v�deo em: https://www.youtube.com/user/DaniloMarcusTI
	Curta e Compartilhe
 */
package allbs.registos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends Activity {

	TextView txvNome;
	TextView txvEmail;
	TextView txvFone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// m�todo para criar o banco a tabela de contatos
		criarTabela();

		Button btnSalvar = (Button) findViewById(R.id.btnSalvar);

		txvNome = (TextView) findViewById(R.id.txvNome);
		txvEmail = (TextView) findViewById(R.id.txvEmail);
		txvFone = (TextView) findViewById(R.id.txvFone);

		// a��o do bot�o salvar
		btnSalvar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {

					Contato c = new Contato();
					c.setEmail(txvEmail.getText().toString());
					c.setNome(txvNome.getText().toString());
					c.setTelefone(txvFone.getText().toString());

					// aciona a a��o de salvar
					salvarContato(c);

					// chamar a listagem
					// Intent it = new Intent(MainActivity.this, Listar.class);
					// startActivity(it);

				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), "Error Ocorreu ",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	public void salvarContato(Contato c) {
		SQLiteDatabase db = null;
		try {
			db = openOrCreateDatabase("contatos.db", Context.MODE_PRIVATE, null);

			ContentValues contentInsert = new ContentValues();
			contentInsert.put("nome", c.getNome());
			contentInsert.put("email", c.getEmail());
			contentInsert.put("fone", c.getTelefone());

			db.insert("contatos", null, contentInsert);

			txvNome.setText("");
			txvFone.setText("");
			txvEmail.setText("");

		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "Error ao inserir",
					Toast.LENGTH_SHORT).show();
		} finally {
			Toast.makeText(getApplicationContext(), "Dados Cadastrados",Toast.LENGTH_SHORT).show();
			// chama a listagem
			startActivity(new Intent(getBaseContext(), Listar.class)); 
			db.close();

		}
	}

	public void criarTabela() {
		SQLiteDatabase db = null;
		try {
			db = openOrCreateDatabase("contatos.db", Context.MODE_PRIVATE, null);

			StringBuilder sql = new StringBuilder();
			sql.append("CREATE TABLE IF NOT EXISTS contatos(");
			sql.append("_id integer primary key autoincrement,");
			sql.append("nome varchar(120),");
			sql.append("email varchar(120),");
			sql.append("fone varchar(20))");

			db.execSQL(sql.toString());

		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "Error Ocorreu",
					Toast.LENGTH_LONG).show();
		} finally {
			db.close();
		}
	}

	// �rea de MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		;
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.listarMenu:
			startActivity(new Intent(this, Listar.class)); 
			return true;
		case R.id.menuMenu:
			startActivity(new Intent(this, Menu.class));
			return true;
		case R.id.cadastrarMenu:
			startActivity(new Intent(this, Cadastro.class)); 
			return true;
		}
		return false;
	}

}
/*
 * Feito para aula em canal do Youtube Por Danilo Marcus Acesse o v�deo em:
 * https://www.youtube.com/user/DaniloMarcusTI Curta e Compartilhe
 */