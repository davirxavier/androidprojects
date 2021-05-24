package davi.xavier.todolist

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import davi.xavier.todolist.databinding.ActivityMainBinding
import davi.xavier.todolist.db.category.Category
import davi.xavier.todolist.db.category.CategoryViewModel
import davi.xavier.todolist.db.todo.DatabaseInstance
import davi.xavier.todolist.db.todo.StringDiffChecker
import davi.xavier.todolist.db.todo.TodoViewModel
import davi.xavier.todolist.db.todo.TodoViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var catViewModel: CategoryViewModel
    private val catNameList: MutableList<Category> = mutableListOf()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.addButton.setOnClickListener { 
            add()
        }

        todoViewModel = ViewModelProvider(this,
            TodoViewModelFactory(DatabaseInstance.getInstance(this).todoDao())).get(TodoViewModel::class.java)
        catViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        catViewModel.getAll().observe(this) {
            catNameList.clear()
            catNameList.addAll(it)
        }

        updateLayout()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig);
        updateLayout()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menu.clear()
            catNameList.forEachIndexed { index, c -> menu.add(0, index, 0, c.name) }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        todoViewModel.filterTodosByCat(catNameList[item.itemId].id, this)
        
        return false
    }
    
    private fun updateLayout() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            supportFragmentManager.findFragmentById(R.id.catFrag)?.let {
                supportFragmentManager.beginTransaction()
                    .show(it)
                    .commit()
            }
        }
        else 
        {
            supportFragmentManager.findFragmentById(R.id.catFrag)?.let {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .commit()
            }
        }
    }
    
    private fun add() {
        val text = binding.textField.text.toString()
        if (text.isNotEmpty())
        {
            catViewModel.getAll().observe(this, {
                var checkedItem = 0
                
                AlertDialog.Builder(this)
                    .setSingleChoiceItems(it.map { cat -> cat.name ?: "" }.toTypedArray(), 
                        checkedItem) { _: DialogInterface, i: Int -> checkedItem = i }
                    .setTitle("Escolha uma categoria")
                    .setPositiveButton("Confirmar") { _: DialogInterface, _: Int -> 
                        todoViewModel.newTodo(text, it[checkedItem].id)
                        binding.textField.setText("")
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            })
        }
    }
    
}
