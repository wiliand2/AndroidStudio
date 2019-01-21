package br.com.alura.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.model.Nota;

public class NotaDAO2 extends SQLiteOpenHelper {

    private final static ArrayList<Nota> notas = new ArrayList<>();


    public NotaDAO2(Context context) {
        super(context,"Notas",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Notas (id INTEGER PRIMARY KEY, Titulo TEXT NOT NULL, Descricao TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Notas";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Nota notas){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("Titulo",notas.getTitulo());
        dados.put("Descricao",notas.getDescricao());

        db.insert("Notas",null,dados);
    }

    public List<Nota> todos(){
        String sql = "SELECT * FROM Notas;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Nota> notas = new ArrayList<Nota>();
        while(c.moveToNext()){
            Nota nota = new Nota();
            nota.setId(c.getLong(c.getColumnIndex("id")));
            nota.setTitulo(c.getString(c.getColumnIndex("Titulo")));
            nota.setDescricao(c.getString(c.getColumnIndex("Descricao")));

            notas.add(nota);
        }
        c.close();

        return notas;
    }

    public void remove(Nota nota){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {nota.getId().toString()};
        db.delete("Notas","id = ?",params);
    }



    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(notas, posicaoInicio, posicaoFim);
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
    }
}

