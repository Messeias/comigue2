package comigue.com.br.comigue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comigue.com.br.comigue.pojo.Horario;
import comigue.com.br.comigue.pojo.Materia;
import comigue.com.br.comigue.pojo.Usuario;

/**
 * Created by Usuario on 07/11/2017.
 */

public class NovaMateriaActivity extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private int dia, mes, ano, hora, minuto;
    private int diaF, mesF, anoF, horaF, minutoF;
    private ArrayList<Horario> horarios;
    private TextView horariosTV;
    private EditText nomeM, descM;
    private RadioGroup restricao;
    private boolean publico;
    private Usuario usuario;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_materia);
        inicializaComponentes();
    }

    public void addHorario(View v){
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, ano, mes, dia);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i1, int i2, int i3){
        anoF = i1;
        mesF = i2;
        diaF = i3;

        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hora, minuto, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i1, int i2){
        horaF = i1;
        minutoF = i2;

        Calendar c = Calendar.getInstance();
        c.set(anoF, mesF, diaF, horaF, minutoF);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE/ HH:mm");
        Date d = new Date();
        try{
            String dataO = sdf.format(c.getTime());
            d = sdf.parse(dataO);
        } catch (Exception e){
            Log.i(e.getMessage(), "onTimeSet: ");
        }

        if(horariosTV.getText().toString().equals("- - - - - - - - - - ")){
            horariosTV.setText(sdf.format(d).toUpperCase());
        } else {
            horariosTV.setText(horariosTV.getText().toString() + " | " + sdf.format(d).toUpperCase());
        }

        Horario h = new Horario();
        h.setHora(c.getTime());
        horarios.add(h);

        for(Horario hr: horarios){
            Log.i(hr.getHora().toString(), "onTimeSet: ");
        }
    }

    public void inicializaComponentes(){
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        horarios = new ArrayList<>();
        horariosTV = (TextView) findViewById(R.id.nova_materia_horarios);

        nomeM = (EditText) findViewById(R.id.nova_materia_nome);
        descM = (EditText) findViewById(R.id.nova_materia_desc);

        restricao = (RadioGroup) findViewById(R.id.nova_materia_restricao);
        restricao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton) group.findViewById(checkedId);
                String t = btn.getText().toString();
                switch (t){
                    case "Público": publico = true; break;
                    case "Privado": publico = false; break;
                }
            }
        });

    }

    public void proximaEtapaMateria(View v){
        if(verificaCampos()){
            Bundle bd = new Bundle();
            bd.putSerializable("usuario", usuario);


            Materia materia = new Materia();
            materia.setNome(nomeM.getText().toString());
            materia.setDescricao(descM.getText().toString());
            materia.setHorarios(horarios);
            materia.setPublico(publico);

            bd.putSerializable("materia", materia);

            Intent i = new Intent(this, NovoPlanoDeEnsinoActivity.class);
            i.putExtras(bd);
            startActivity(i);

        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean verificaCampos(){
        boolean nP = !nomeM.getText().toString().isEmpty();
        boolean hP = !horariosTV.getText().toString().equals("- - - - - - - - - - ");
        boolean dP = !descM.getText().toString().isEmpty();

        return nP && hP && dP;
    }
}
