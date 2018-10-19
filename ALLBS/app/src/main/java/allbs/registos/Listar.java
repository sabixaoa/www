package allbs.registos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Listar extends Activity implements OnItemClickListener {

	SQLiteDatabase db;
	Cursor cursor;
	SimpleCursorAdapter ad;
	ListView listViewContatos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar);

		// Buscar os dados
		buscarDados();
			
		// Setar o Adapter
		criarListagem();
		
	}

	public void buscarDados(){
		
		try{
			db = openOrCreateDatabase("contatos.db",Context.MODE_PRIVATE, null);
			cursor = db.rawQuery("SELECT * from contatos", null);
						
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void criarListagem(){
		
		// pega o listview que conter� os itens
		listViewContatos = (ListView) findViewById(R.id.list);
		/* 
		 * TODO
		 * Inserir o campo id para referenciar o Contato para altera��o 
		 * Buscar o campo TextView do arquivo XML onde � feita a listagem
		*/
		String[] from = {"_id","nome", "email", "fone"}; // nome dos campos da tabela
		int[] to = {R.id.txvId, R.id.txvContatoNome, R.id.txvContatoEmail, R.id.txvContatoFone};// campos do model
				
		ad = new SimpleCursorAdapter(getApplicationContext(), R.layout.model_list_view_contatos, cursor, from, to);
		
		// habilita o click no item da lista
		listViewContatos.setOnItemClickListener(this);
		
		listViewContatos.setAdapter(ad);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		// Forma 1
		//Cursor c = (Cursor) ad.getItem(position);
		//String nome = c.getString(c.getColumnIndex("nome"));
		
		SQLiteCursor sqlCursor = (SQLiteCursor) ad.getItem(position);
		String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nome"));
		
		/* TODO - obter o Id do objeto selecionado e enviar para a altera��o */
		String id_contato = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));
		Toast.makeText(getApplicationContext(), "Selecionou o nome: " + nome + " e id: "+id_contato, Toast.LENGTH_SHORT).show();
		
		// chama a tela de altera��o
		Intent altera = new Intent(getApplicationContext(), Alterar.class);
		altera.putExtra("id_contato", id_contato);
		startActivity(altera);
		
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
