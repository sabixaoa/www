package allbs.registos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Alterar extends Activity {

	EditText txvId_a;
	EditText txvNome_a;
	EditText txvEmail_a;
	EditText txvFone_a;

	Button btnAlterar;
	
	String id_contato;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alterar);

		txvId_a = (EditText) findViewById(R.id.txvId_a);
		txvNome_a = (EditText) findViewById(R.id.txvNome_a);
		txvEmail_a = (EditText) findViewById(R.id.txvEmail_a);
		txvFone_a = (EditText) findViewById(R.id.txvFone_a);
		btnAlterar = (Button) findViewById(R.id.btnAlterar);

		Intent intent = getIntent();

		if (intent != null) {
			Bundle params = intent.getExtras();
			if (params != null) {
				id_contato = params.getString("id_contato");
				buscaContato(id_contato);
			}
		}

		btnAlterar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String id_contato = txvId_a.getText().toString();
				int res = alterarContato(id_contato);
				if (res > 0) {
					Toast.makeText(getApplicationContext(),
							"Alterado com sucesso", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getApplicationContext(),
							Listar.class));
				} else {
					Toast.makeText(getApplicationContext(), "Error ao alterar",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	protected int alterarContato(String id_contato) {

		SQLiteDatabase db = openOrCreateDatabase("contatos.db",
				Context.MODE_PRIVATE, null);

		String nome = txvNome_a.getText().toString();
		String email = txvEmail_a.getText().toString();
		String fone = txvFone_a.getText().toString();

		ContentValues ctv = new ContentValues();
		ctv.put("nome", nome);
		ctv.put("email", email);
		ctv.put("fone", fone);
		int res = db.update("contatos", ctv, "_id=?",
				new String[] { id_contato });
		db.close();
		return res;

	}

	// fun��o para obter o Contato clicado
	private void buscaContato(String id_contato) {

		SQLiteDatabase db = openOrCreateDatabase("contatos.db",
				Context.MODE_PRIVATE, null);

		String sql = "SELECT * from contatos where _id=?";

		Cursor c = (SQLiteCursor) db.rawQuery(sql, new String[] { id_contato });

		if (c.moveToFirst()) {
			String id = c.getString(c.getColumnIndex("_id"));
			String nome = c.getString(c.getColumnIndex("nome"));
			String email = c.getString(c.getColumnIndex("email"));
			String fone = c.getString(c.getColumnIndex("fone"));

			// Log.i("teste: ", id +" " +nome);

			txvId_a.setText(id.toString());
			txvNome_a.setText(nome.toString());
			txvEmail_a.setText(email.toString());
			txvFone_a.setText(fone.toString());
		}
		c.close(); // fecha a conex�o
		db.close();
	}

	// m�todo para excluir
	public void excluir(View v) {

		id_contato = txvId_a.getText().toString();
		
		try {
			SQLiteDatabase db = openOrCreateDatabase("contatos.db",Context.MODE_PRIVATE, null);
			db.delete("contatos","_id=?", new String[] { id_contato });
			db.close();
			Toast.makeText(getApplicationContext(), "Exclu�do com sucesso", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(getApplicationContext(),Listar.class));
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Erro ao excluir", Toast.LENGTH_SHORT).show();
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
