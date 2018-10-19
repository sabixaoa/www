package allbs.registos;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_menu);

		String[] menus = {"Cadastrar", "Listar"};
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, menus);
				
		setListAdapter(adapter);
	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
			
		switch (position) {
		case 0:
			startActivity(new Intent(getBaseContext(),Cadastro.class));
			break;
		case 1:
			startActivity(new Intent(getBaseContext(),Listar.class));
			break;
		default:
			break;
		}
	
	}
	
}
