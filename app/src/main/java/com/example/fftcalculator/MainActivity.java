package com.example.fftcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=findViewById(R.id.btn);
        final TextView tv=findViewById(R.id.tv);
        final EditText et=findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String[] fftArray=et.getText().toString().split(" ");
                ArrayList<String> fftArray1=new ArrayList<String>();
                for(int i=0;i<fftArray.length;i++){
                    fftArray1.add(fftArray[i]);
                }
                String fftAnswer=fftCalculate(fftArray1);
                tv.setText(fftAnswer);
            }
        });
    }

    private String fftCalculate(ArrayList<String> fftArray) {
        int len=fftArray.size();
        int y;
        if(check(len)){
           y=(int) (Math.log(len)/Math.log(2));
        }
        else{
            y= (int) (Math.log(len)/Math.log(2)) + 1;
            int N= (int) Math.pow(2,y);
            for(int i=0;i<(N-len);i++){
                fftArray.add("0");
            }
        }
        String[] Str=bitRev(fftArray);
        int No=Str.length;
        for(int j=1;j<=y;j++){
            int L= (int) Math.pow(2,j);
            for(int k=0;k<=(No-L);k+=L){
                for(int n=0;n<=(L/2-1);n++){
                    String U=Str[n+k];
                    String V=Str[n+k+(L/2)];
                    Str[n+k]=complexAdd(U,V,n,L);
                    Str[n+k+(L/2)]=complexSub(U,V,n,L);
                }
            }
        }
        StringBuilder stringBuilder=new StringBuilder();
        for(int j=0;j<No;j++){
            stringBuilder.append(Str[j] + " ");
        }
        String w=stringBuilder.toString();
        return w;
    }

    private String complexSub(String x, String y, int n, int L) {
        double radian=-1*2*3.14*n/L;
        int c=(int) Math.round(Math.cos(radian));
        int s=(int) Math.round(Math.sin(radian));
        if(x.contains("+i") && y.contains("+i")) {
            String[] S=x.split("\\+i");
            String[] T=y.split("\\+i");
            int B1=Integer.parseInt(T[0])*c-Integer.parseInt(T[1])*s;
            int B2=Integer.parseInt(T[1])*c+Integer.parseInt(T[0])*s;

            int rel=Integer.parseInt(S[0])-B1;
            int img=Integer.parseInt(S[1])-B2;

            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else if(!x.contains("+i") && y.contains("+i")) {
            String[] S=x.split("\\+i");
            int B1=Integer.parseInt(y)*c;
            int B2=Integer.parseInt(y)*s;
            int rel=Integer.parseInt(S[0])-B1;
            int img=Integer.parseInt(S[1])-B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else if(x.contains("+i") && !y.contains("+i")) {
            String[] T=y.split("\\+i");
            int B1=Integer.parseInt(T[0])*c-Integer.parseInt(T[1])*s;
            int B2=Integer.parseInt(T[1])*c+Integer.parseInt(T[0])*s;
            int rel=Integer.parseInt(x)-B1;
            int img=-B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else {
            int B1=Integer.parseInt(y)*c;
            int B2=Integer.parseInt(y)*s;
            int rel=Integer.parseInt(x)-B1;
            int img=-B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
    }

    private String complexAdd(String x, String y, int n, int L) {
        double radian=-1*2*3.14*n/L;
        int c=(int) Math.round(Math.cos(radian));
        int s=(int) Math.round(Math.sin(radian));
        if(x.contains("+i") && y.contains("+i")) {
            String[] S=x.split("\\+i");
            String[] T=y.split("\\+i");
            int B1=Integer.parseInt(T[0])*c-Integer.parseInt(T[1])*s;
            int B2=Integer.parseInt(T[1])*c+Integer.parseInt(T[0])*s;

            int rel=Integer.parseInt(S[0])+B1;
            int img=Integer.parseInt(S[1])+B2;

            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else if(!x.contains("+i") && y.contains("+i")) {
            String[] S=x.split("\\+i");
            int B1=Integer.parseInt(y)*c;
            int B2=Integer.parseInt(y)*s;
            int rel=Integer.parseInt(S[0])+B1;
            int img=Integer.parseInt(S[1])+B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else if(x.contains("+i") && !y.contains("+i")) {
            String[] T=y.split("\\+i");
            int B1=Integer.parseInt(T[0])*c-Integer.parseInt(T[1])*s;
            int B2=Integer.parseInt(T[1])*c+Integer.parseInt(T[0])*s;
            int rel=Integer.parseInt(x)+B1;
            int img=B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
        else {
            int B1=Integer.parseInt(y)*c;
            int B2=Integer.parseInt(y)*s;
            int rel=Integer.parseInt(x)+B1;
            int img=B2;
            String m=String.valueOf(rel)+"+i"+String.valueOf(img);
            return m;
        }
    }

    private String[] bitRev(ArrayList<String> fftArray) {
        int l=fftArray.size();
        int[] arr=revOrder(l);
        ArrayList<String> fftArray1=new ArrayList<String>();
        for(int i=0;i<l;i++){
            fftArray1.add(fftArray.get(arr[i]));
        }
        String[] str=new String[l];
        for(int i=0;i<l;i++){
            str[i]=fftArray1.get(i);
        }
        return str;
    }
    private  boolean check(int len) {
        if(len==0) {
            return false;
        }
        // TODO Auto-generated method stub
        return (int)(Math.ceil(Math.log(len)/Math.log(2)))==(int)(Math.floor(Math.log(len)/Math.log(2)));
    }

    private int[] revOrder(int l) {
        String str=Integer.toBinaryString(l-1);
        int len=str.length();
        ArrayList<String> fftArray1=new ArrayList<String>();
        for(int i=0;i<(l-1);i++) {
            String str2=String.format("%" + len + "s", Integer.toBinaryString(i)).replace(" ","0");
            fftArray1.add(str2);
        }
        fftArray1.add(str);

        ArrayList<String> fftArray2=new ArrayList<String>();
        for(int i=0;i<l;i++){
            StringBuilder stringBuilder=new StringBuilder();
            fftArray2.add(stringBuilder.append(fftArray1.get(i)).reverse().toString());
        }
        int[] array=new int[l];
        for(int i=0;i<l;i++) {
            array[i]=Integer.parseInt(fftArray2.get(i),2);
        }
        return array;
    }

}
