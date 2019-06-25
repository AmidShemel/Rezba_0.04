package rezba_0.pkg01;

//last date 27.03.2019

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {
    
    int multiplier;
    protected static String defaultYear;
    String odM, steP;
    String[] sItem, odD1, Radius, licCodes;
    String[] stepsTo1, stepsTo1_4, stepsTo2_8, stepsTo5_6, stepsTo11_2, stepsTo22_4;
    String[] allSteps, stepsTo45, stepsTo90, stepsTo180, stepsTo355, stepsTo600;
    String[] Tolerance, TolLatters, OdLetters, IdLetters;

    public MainFrame() {
        this.multiplier = 0;
        
        /* Набір всіх стандартних кроків метричної різьби */
        this.allSteps = new String[]{"0.075", "0.08", "0.09", "0.1", "0.125", "0.15", "0.175", "0.2", "0.25", "0.3", "0.35", "0.4", "0.45", "1.25", "1.5", "1.75", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "8"};

        /* Набір кроків валідних діаметру різьби */
        this.stepsTo1 = new String[]{"0.075", "0.08", "0.09", "0.1", "0.125", "0.15", "0.175"};
        this.stepsTo1_4 = new String[]{"0.2", "0.25", "0.3"};
        this.stepsTo2_8 = new String[]{"0.2", "0.25", "0.35", "0.4", "0.45"};
        this.stepsTo5_6 = new String[]{"0.25", "0.35", "0.5", "0.6", "0.7", "0.75", "0.8"};
        this.stepsTo11_2 = new String[]{"0.25", "0.3", "0.5", "0.75", "1", "1.25", "1.5"};
        this.stepsTo22_4 = new String[]{"0.35", "0.5", "0.75", "1", "1.25", "1.5", "1.75", "2", "2.5"};
        this.stepsTo45 = new String[]{"0.5", "0.75", "1", "1.5", "2", "3", "3.5", "4", "4.5"};
        this.stepsTo90 = new String[]{"0.5", "0.75", "1", "1.5", "2", "3", "4", "5", "5.5", "6"};
        this.stepsTo180 = new String[]{"0.75", "1", "1.5", "2", "3", "4", "6", "8"};
        this.stepsTo355 = new String[]{"1.5", "2", "3", "4", "6", "8"};
        this.stepsTo600 = new String[]{"2", "4", "6", "8"};
        this.OdLetters = new String[] {"d","e","f","g","h"};
        this.IdLetters = new String[] {"E","F","G","H"};
        
        this.licCodes = new String[]{"Th3xySShpxUm9nDx", "y6E833XM3OKt_as4", "i8TRF9VXFU9jzOia", "EKrGawmd0U640wbd", "jdIrDmnkOGFksbEf"};
        defaultYear = "2018"; // Ліцензія за замовчанням до: 
        
        /* Зповнення спичку радіусів впадини */
        this.Radius = new String[55];
        double dr = 0;
        for(int i = 0; i < Radius.length; i++){
            Radius[i] = "R" + roundDouble(dr = dr + 0.01);
        }

        /* Зповнення спичку різьб */
        this.odD1 = new String[331];
        double d1 = 0.95, d2 = 0.05;
        for(int i = 0; i<odD1.length; i++){
            d1 = d1 + d2;
            odD1[i] = "M " + roundDouble(d1);
            //odD1[i] = "M " + Double.toString(roundDouble(d));
            if(d1 < 0.6){d2 = 0.05;}
                else if (d1 < 1.2){d2 = 0.10;}
                else if (d1 < 2.2){d2 = 0.2;}
                else if (d1 <= 2.4){d1 = 2; d2 = 0.5;}
                else if (d1 < 54){d2 = 0.5;}
                else if (d1 < 106){d2 = 1;}
                else if (d1 < 400){d2 = 2;}
                else d2 = 10;}

        /* Створення списку допусків (для OD діаметру) */
        this.Tolerance = new String[OdLetters.length * 3];
        int i = 0;
        for (String allToleranceLetter : OdLetters) {
            for (int j = 4; j <= 8; j=j+2) {
                for (int k = i; k < Tolerance.length; k++) {
                    Tolerance[k] = Integer.toString(j) + allToleranceLetter;
                } i++;
            }
        }

        initComponents();
        startMainMetricTreads();
        Settings.Restore();
        checkLicData();
    }
    
    /* Перевірка дати ліцензії */
    private void checkLicData(){
        int limitYear;
        if (defaultYear.equals(licCodes[0])){
            limitYear = 2019;
        } else if (defaultYear.equals(licCodes[1])){
             limitYear = 2020;
        } else if (defaultYear.equals(licCodes[2])){
             limitYear = 2021;
        } else if (defaultYear.equals(licCodes[3])){
             limitYear = 2022;
        } else if (defaultYear.equals(licCodes[4])){
             limitYear = 2023;
        } else limitYear = 2018;
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = 1 + Calendar.getInstance().get(Calendar.MONTH);
        System.err.println("Today: " + curMonth + "." + curYear);
        if(curYear <= limitYear){
            if(curMonth == 12) {
                JOptionPane.showMessageDialog(this, "Ліцензія до 31.12." + limitYear + " р.\nЗверніться: hizz@ukr.net", "Ліцензія", JOptionPane.OK_OPTION);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ліцензія закінчилась в " + limitYear + "-му р.\nЗверніться: hizz@ukr.net", "Ліцензія", JOptionPane.CLOSED_OPTION);
            String inputCode = JOptionPane.showInputDialog(this, "Input CODE", "License code", JOptionPane.OK_CANCEL_OPTION);

            if (inputCode.equals("")) {
                JOptionPane.showMessageDialog(this, "Поле вводу пусте", "Помилка", JOptionPane.OK_OPTION);
                System.exit(0);
            } else if (inputCode.equals(licCodes[0])) {
                defaultYear = licCodes[0];
            } else if (inputCode.equals(licCodes[1])){
                defaultYear = licCodes[1];
            } else if (inputCode.equals(licCodes[2])){
                defaultYear = licCodes[2];
            } else if (inputCode.equals(licCodes[3])){
                defaultYear = licCodes[3];
            } else if (inputCode.equals(licCodes[4])){
                defaultYear = licCodes[4];
            } else {
                JOptionPane.showMessageDialog(this, "Код авторизації не вірний", "Помилка", JOptionPane.OK_OPTION);
                System.exit(0);
            }
        }
    }
    
    private void startMainMetricTreads(){
        setToleransesIn1();
        setToleransesIn2();
        jComboMetricThread.setSelectedItem("M 6");
        jComboMetricTolerance1.setSelectedItem("6g");
        jLblIdR.setVisible(false);
    }

    /* Визначення допусків */
    private void ReadWriteToleranses(){
        String tol1 = (String) jComboMetricTolerance1.getSelectedItem();
        String tol2 = (String) jComboMetricTolerance2.getSelectedItem();
        String let1 = tol1.substring(1);
        String let2 = tol2.substring(1);
        int num1 = Integer.parseInt(tol1.substring(0,1));
        int num2 = Integer.parseInt(tol2.substring(0,1));
        double step = Double.parseDouble(jLblMainP.getText());
        double d, d2 = Double.parseDouble(jLblD2.getText().substring(1));
        int lineInTables1 = 0, colLatters1 = 0, colLatters2 = 0, colToleranceID = 0,
            colToleranceOD = 0, colToleranceMID = 0, colToleranceMOD = 0, toleranceMain = 0,
            deflection2 = 0, deflection3 = 0, deflection1 = 0, toleranceMidle = 0;

        if(jRadioOD.isSelected()){
            jLabel3.setText("Зовнішній діаметр");
            d = Double.parseDouble(jLblD.getText().substring(1));
        } else {
            jLabel3.setText("Внутрішній діаметр");
            d = Double.parseDouble(jLblD1.getText().substring(1));
        }

        if(step == 0.20)lineInTables1 = 0;
//<editor-fold defaultstate="collapsed" desc="Вызначення рядку в таблиці">
        else if(step == 0.25)lineInTables1 = 1;
        else if(step == 0.30)lineInTables1 = 2;
        else if(step == 0.35)lineInTables1 = 3;
        else if(step == 0.40)lineInTables1 = 4;
        else if(step == 0.45)lineInTables1 = 5;
        else if(step == 0.50)lineInTables1 = 6;
        else if(step == 0.60)lineInTables1 = 7;
        else if(step == 0.70)lineInTables1 = 8;
        else if(step == 0.75)lineInTables1 = 9;
        else if(step == 0.80)lineInTables1 = 10;
        else if(step == 1.00)lineInTables1 = 11;
        else if(step == 1.25)lineInTables1 = 12;
        else if(step == 1.50)lineInTables1 = 13;
        else if(step == 1.75)lineInTables1 = 14;
        else if(step == 2.00)lineInTables1 = 15;
        else if(step == 2.50)lineInTables1 = 16;
        else if(step == 3.00)lineInTables1 = 17;
        else if(step == 3.50)lineInTables1 = 18;
        else if(step == 4.00)lineInTables1 = 19;
        else if(step == 4.50)lineInTables1 = 20;
        else if(step == 5.00)lineInTables1 = 21;
        else if(step == 5.50)lineInTables1 = 22;
        else if(step == 6.00)lineInTables1 = 23;
        else if(step == 8.00)lineInTables1 = 24;
        else System.out.println("this step is not recommended");
//</editor-fold>

        switch (num1) {
//<editor-fold defaultstate="collapsed" desc="Перемикач по цифрі">
            case 3:
                colToleranceID = 0;
                colToleranceOD = 0;
                colToleranceMID = 0;
                colToleranceMOD = 0;
                break;
            case 4:
                colToleranceID = 0;
                colToleranceOD = 0;
                colToleranceMID = 0;
                colToleranceMOD = 1;
                break;
            case 5:
                colToleranceID = 1;
                colToleranceOD = 1;
                colToleranceMID = 1;
                colToleranceMOD = 2;
                break;
            case 6:
                colToleranceID = 2;
                colToleranceOD = 1;
                colToleranceMID = 2;
                colToleranceMOD = 3;
                break;
            case 7:
                colToleranceID = 3;
                colToleranceOD = 1;
                colToleranceMID = 3;
                colToleranceMOD = 4;
                break;
            case 8:
                colToleranceID = 4;
                colToleranceOD = 2;
                colToleranceMID = 4;
                colToleranceMOD = 5;
                break;
            case 9:
                colToleranceID = 4;
                colToleranceOD = 2;
                colToleranceMID = 5;
                colToleranceMOD = 6;
                break;
            case 10:
                colToleranceID = 4;
                colToleranceOD = 2;
                colToleranceMID = 5;
                colToleranceMOD = 7;
                break;
            default:
                System.out.println("wrong num of tolerance");
                break;
        }
//</editor-fold>
        switch (let1) {
            //<editor-fold defaultstate="collapsed" desc="Перемикач по букві">
            case "d":
                colLatters1 = 0;
                deflection1 = 0 - Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = 0 - Tables.tabToleranceOD[lineInTables1][colToleranceOD];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "e":
                colLatters1 = 1;
                deflection1 = 0 - Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = 0 - Tables.tabToleranceOD[lineInTables1][colToleranceOD];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "f":
                colLatters1 = 2;
                deflection1 = 0 - Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = 0 - Tables.tabToleranceOD[lineInTables1][colToleranceOD];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "g":
                colLatters1 = 3;
                deflection1 = 0 - Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = 0 - Tables.tabToleranceOD[lineInTables1][colToleranceOD];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "h":
                colLatters1 = 4;
                deflection1 = 0 - Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = 0 - Tables.tabToleranceOD[lineInTables1][colToleranceOD];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "D":
                colLatters1 = 0;
                deflection1 = Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = Tables.tabToleranceID[lineInTables1][colToleranceID];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "E":
                colLatters1 = 1;
                deflection1 = Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = Tables.tabToleranceID[lineInTables1][colToleranceID];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "F":
                colLatters1 = 2;
                deflection1 = Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = Tables.tabToleranceID[lineInTables1][colToleranceID];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "G":
                colLatters1 = 3;
                deflection1 = Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = Tables.tabToleranceID[lineInTables1][colToleranceID];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "H":
                colLatters1 = 4;
                deflection1 = Tables.tabDeflection[lineInTables1][colLatters1];
                toleranceMain = Tables.tabToleranceID[lineInTables1][colToleranceID];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            default:
                System.out.println("wrong letter of tollerance");
                break;

        }//</editor-fold>

        if(jCheckSecondTolerance.isSelected()){
            switch (num2) {
                //<editor-fold defaultstate="collapsed" desc="Перемикача по цифрі">
            case 3:
                colToleranceMID = 0;
                colToleranceMOD = 0;
                break;
            case 4:
                colToleranceMID = 0;
                colToleranceMOD = 1;
                break;
            case 5:
                colToleranceMID = 1;
                colToleranceMOD = 2;
                break;
            case 6:
                colToleranceMID = 2;
                colToleranceMOD = 3;
                break;
            case 7:
                colToleranceMID = 3;
                colToleranceMOD = 4;
                break;
            case 8:
                colToleranceMID = 4;
                colToleranceMOD = 5;
                break;
            case 9:
                colToleranceMID = 5;
                colToleranceMOD = 6;
                break;
            case 10:
                colToleranceMID = 5;
                colToleranceMOD = 7;
                break;
            default:
                System.out.println("wrong num of tollerance");
                break;
        }//</editor-fold>
            switch (let2) {
                //<editor-fold defaultstate="collapsed" desc="Перемикач по букві">
            case "d":
                colLatters2 = 0;
                deflection2 = 0 - Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "e":
                colLatters2 = 1;
                deflection2 = 0 - Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "f":
                colLatters2 = 2;
                //buferTab2 = Tables.tabToleranceMOD;
                deflection2 = 0 - Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "g":
                colLatters2 = 3;
                //buferTab2 = Tables.tabToleranceMOD;
                deflection2 = 0 - Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "h":
                colLatters2 = 4;
                deflection2 = 0 - Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = 0 - Tables.tabToleranceMOD[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMOD];
                break;
            case "D":
                colLatters2 = 0;
                deflection2 = Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "E":
                colLatters2 = 1;
                deflection2 = Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "F":
                colLatters2 = 2;
                deflection2 = Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "G":
                colLatters2 = 3;
                deflection2 = Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            case "H":
                colLatters2 = 4;
                //buferTab2 = Tables.tabToleranceMID;
                deflection2 = Tables.tabDeflection[lineInTables1][colLatters2];
                toleranceMidle = Tables.tabToleranceMID[multiplier + jComboMetricSteps.getSelectedIndex()][colToleranceMID];
                break;
            default:
                System.out.println("wrong letter of tollerance");
                break;
        }//</editor-fold>
            deflection3 = deflection2;
        } else {
            deflection3 = deflection1;
        }

        double max1 = deflection1/1000.0 + (double) d;
        double min1 = toleranceMain/1000.0 + deflection1/1000.0 + d;
        double mid1 = (max1 + min1) / 2;

        double max2 = deflection3/1000.0 + (double) d2;
        double min2 = toleranceMidle/1000.0 + deflection3/1000.0 + d2;
        double mid2 = (max2 + min2) / 2;

        jLabel5.setText(jRadioOD.isSelected() ? roundDouble(max1): roundDouble(min1));
        jLabel7.setText(roundDouble(mid1));
        jLabel9.setText(jRadioOD.isSelected() ? roundDouble(min1) : roundDouble(max1));
        
        jLabel12.setText(jRadioOD.isSelected() ? roundDouble(max2): roundDouble(min2));
        jLabel14.setText(roundDouble(mid2));
        jLabel16.setText(jRadioOD.isSelected() ? roundDouble(min2): roundDouble(max2));
        jLabel17.setText(roundDouble(d));
        jLabel20.setText(roundDouble(deflection1/1000.0));
        jLabel21.setText(roundDouble(deflection1/1000.0 + toleranceMain/1000.0));
        jLabel22.setText(roundDouble(deflection3/1000.0));
        jLabel23.setText(roundDouble(toleranceMidle/1000.0 + deflection3/1000.0));
        jLabel24.setText(jLblD2.getText().substring(1));
    }

    /* Оновлює всі поля та показники після будь-яких змін */
    private void refresMetrics(){
        mathID();
        setTextToMainLbl();
        ReadWriteToleranses();
    }

    /* Головні обчисчлення */
    private void mathID(){
        double P = Double.parseDouble((String)jComboMetricSteps.getSelectedItem());
        double R = (jCheckBoxRadius.isSelected() ? Double.parseDouble(((String) jComboRadius.getSelectedItem()).substring(1)) : 0.14434);
        double H = 0.866025404 * P;
        double L = 0.541265877 * P;
        double L1 = (jRadioID.isSelected() ? (0.10825 * P + L) - R : (H/4 * P + L) - R); // формула вирахування глибини впадини вираховується залежить від ОД чи ІД
        double D = Double.parseDouble(((String) jComboMetricThread.getSelectedItem()).substring(2));
        double D1 = D - L1 * 2;
        double D2 = D - 0.324759526 * P * 2;
        double D3 = D - (0.6250 * H * 2);
        double D4 = (H/8.0 - R) * 2 + D;
        if(jRadioID.isSelected()){
           jLblD.setText("Ø" + (roundDouble(D4)));
           jLblD1.setText("Ø" + (roundDouble(D3)));
        } else {
            jLblD.setText("Ø" + (roundDouble(D)));
            jLblD1.setText("Ø" + (roundDouble(D1)));
            }
        jLblD2.setText("Ø" + (roundDouble(D2)));
        jLblMainP.setText((String) jComboMetricSteps.getSelectedItem());
        jLblMainL.setText(roundDouble(L1));
        jLblIdR.setText(roundDouble(R));
        jLblOdR.setText(roundDouble(R));
    }

     /* Виклик головного зображення */
    protected void updateMainImg(String name) {
        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("images/" + name + ".png"));
        jLabelMainImg.setIcon(icon);
    }

    /* Вибір типу різьби (наружна/внутрішня) */
    private void setOdSelected(boolean od){
        jRadioOD.setSelected(od);
        jRadioID.setSelected(!od);
    }

    /* Вибір рекомендованого кроку для різьби */
    private void setDefaultStepForThread(){
        double d = Double.parseDouble(((String) jComboMetricThread.getSelectedItem()).substring(1));
        if (d < 1){jComboMetricSteps.setSelectedItem("");}
        else if (d < 1.4) jComboMetricSteps.setSelectedItem("0.3");
        else if (d <= 2.5) jComboMetricSteps.setSelectedItem("0.4");
        else if (d <= 3) jComboMetricSteps.setSelectedItem("0.5");
        else if (d <= 4) jComboMetricSteps.setSelectedItem("0.7");
        else if (d <= 5.5) jComboMetricSteps.setSelectedItem("0.8");
        else if (d <= 7) jComboMetricSteps.setSelectedItem("1");
        else if (d <= 9) jComboMetricSteps.setSelectedItem("1.25");
        else if (d <= 11) jComboMetricSteps.setSelectedItem("1.5");
        else if (d <= 13) jComboMetricSteps.setSelectedItem("1.75");
        else if (d <= 16) jComboMetricSteps.setSelectedItem("2");
        else if (d <= 22) jComboMetricSteps.setSelectedItem("2.5");
        else if (d <= 27) jComboMetricSteps.setSelectedItem("3");
        else if (d <= 33) jComboMetricSteps.setSelectedItem("3.5");
        else if (d <= 39) jComboMetricSteps.setSelectedItem("4");
        else if (d <= 48) jComboMetricSteps.setSelectedItem("4.5");
        else jComboMetricSteps.setSelectedItem("");
    }

    /* Валідація кроків під діаметр різьби */
    private  void setValidStepsForDiam() {
        double d = Double.parseDouble(((String) jComboMetricThread.getSelectedItem()).substring(1));
        if (d <= 0.9){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo1)); multiplier = 0;
        } else if (d <= 1.4){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo1_4)); multiplier = 0;
        } else if (d <= 2.8){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo2_8)); multiplier = 3;
        } else if (d <= 5.6){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo5_6)); multiplier = 8;
        } else if (d <= 11.2){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo11_2)); multiplier = 15;
        } else if (d <= 22.4){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo22_4)); multiplier = 22;
        } else if (d <= 45){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo45)); multiplier = 31;
        } else if (d <= 90){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo90)); multiplier = 40;
        } else if (d <= 180){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo180)); multiplier = 50;
        } else if (d <= 355){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo355)); multiplier = 58;
        } else if (d <= 600){jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo600)); multiplier = 64;
        } else jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(allSteps));
    }

    /* Додання тексту в головний надпис */
    private void setTextToMainLbl(){
        double R = Double.parseDouble(((String) jComboRadius.getSelectedItem()).substring(1));
        double P = Double.parseDouble((String)jComboMetricSteps.getSelectedItem());
        if(R > 0.14434*P){
            jLabel1.setText("MR" + ((String)jComboMetricThread.getSelectedItem()).substring(1) +
                " x " + (String)jComboMetricSteps.getSelectedItem());
        } else jLabel1.setText((String)jComboMetricThread.getSelectedItem() +
                " x " + (String)jComboMetricSteps.getSelectedItem());
        

        if(jCheckSecondTolerance.isSelected()){
          jLabel1.setText(jLabel1.getText() +
                   " - " + (String)jComboMetricTolerance2.getSelectedItem() +
                  (String)jComboMetricTolerance1.getSelectedItem());
        } else jLabel1.setText(jLabel1.getText() +
                   " - " + (String)jComboMetricTolerance1.getSelectedItem());
    }

    /* Заповнення  першого списку допусків */
    private void setToleransesIn1(){
        int i = 0, n = 8, strt = 4, lim = 8, iter = 1;
        if(jRadioID.isSelected() == false){
            TolLatters = OdLetters;
            n = 3; strt = 4; lim = 8; iter = 2;
        } else {
            TolLatters = IdLetters;
            n = 5; strt = 4; lim = 8; iter = 1;
        }

        this.Tolerance = new String[TolLatters.length * n];

        for (String allToleranceLetter : TolLatters) {
            for (int j = strt; j<=lim; j+=iter) {
                for (int k = i; k < Tolerance.length; k++) {
                    Tolerance[k] = Integer.toString(j) + allToleranceLetter;
                } i++;
            }
        }
        jComboMetricTolerance1.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
        //jComboMetricTolerance2.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
    }

    /* Заповнення другого списку допусків */
    private void setToleransesIn2(){
        int i = 0, n = 8, strt = 4, lim = 8, iter = 1;
        if(jRadioID.isSelected() == false){
            TolLatters = OdLetters;
            n = 7; strt = 3; iter = 1; lim = 9; //повинно бути 10*
        } else {
            TolLatters = IdLetters;
            n = 5; strt = 4; iter = 1; lim = 8; //повинно бути 9*
        }

        this.Tolerance = new String[TolLatters.length * n];

        for (String allToleranceLetter : TolLatters) {
            for (int j = strt; j<=lim; j+=iter) {
                for (int k = i; k < Tolerance.length; k++) {
                    Tolerance[k] = Integer.toString(j) + allToleranceLetter;
                } i++;
            }
        }
        //jComboMetricTolerance1.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
        jComboMetricTolerance2.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
    }

    /* Округлення Double (ПОВЕРТАЄ String) */
    private  static String roundDouble(double d){
        return NumberFormat.getInstance(Locale.US).format(d);
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTab3 = new javax.swing.JTabbedPane();
        jPanelMainTab1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelImage = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLblD = new javax.swing.JLabel();
        jLblD2 = new javax.swing.JLabel();
        jLblD1 = new javax.swing.JLabel();
        jLblMainP = new javax.swing.JLabel();
        jLblIdR = new javax.swing.JLabel();
        jLblOdR = new javax.swing.JLabel();
        jLblMainL = new javax.swing.JLabel();
        jLabelMainImg = new javax.swing.JLabel();
        jPanelItemSelectiont = new javax.swing.JPanel();
        jRadioOD = new javax.swing.JRadioButton();
        jRadioID = new javax.swing.JRadioButton();
        jComboMetricThread = new javax.swing.JComboBox<>();
        jComboMetricSteps = new javax.swing.JComboBox<>();
        jComboMetricTolerance1 = new javax.swing.JComboBox<>();
        jComboRadius = new javax.swing.JComboBox<>();
        jComboMetricTolerance2 = new javax.swing.JComboBox<>();
        jCheckSecondTolerance = new javax.swing.JCheckBox();
        jCheckBoxRadius = new javax.swing.JCheckBox();
        jPanelResultOutput = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        jLabel18.setText("jLabel18");

        jLabel19.setText("jLabel19");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thread");
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(0, 0, 600, 400));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.darkGray);
        setIconImages(null);
        setMinimumSize(null);
        setName("MainFrame"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTab3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTab3.setMaximumSize(new java.awt.Dimension(650, 450));
        jTab3.setMinimumSize(new java.awt.Dimension(650, 450));
        jTab3.setPreferredSize(new java.awt.Dimension(650, 450));

        jPanelMainTab1.setFocusTraversalPolicyProvider(true);
        jPanelMainTab1.setMaximumSize(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Виберіть різьбу");

        jPanelImage.setPreferredSize(new java.awt.Dimension(550, 400));

        jPanel1.setLayout(null);

        jLblD.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblD.setForeground(new java.awt.Color(0, 0, 0));
        jLblD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblD.setText("0.000");
        jLblD.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblD);
        jLblD.setBounds(410, 130, 140, 40);

        jLblD2.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblD2.setForeground(new java.awt.Color(0, 0, 0));
        jLblD2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblD2.setText("0.000");
        jLblD2.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblD2);
        jLblD2.setBounds(350, 210, 160, 40);

        jLblD1.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblD1.setForeground(new java.awt.Color(0, 0, 0));
        jLblD1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblD1.setText("0.000");
        jLblD1.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblD1);
        jLblD1.setBounds(280, 300, 160, 40);

        jLblMainP.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblMainP.setForeground(new java.awt.Color(0, 0, 0));
        jLblMainP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblMainP.setText("0.000");
        jLblMainP.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblMainP);
        jLblMainP.setBounds(85, 320, 100, 40);

        jLblIdR.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblIdR.setForeground(new java.awt.Color(0, 0, 0));
        jLblIdR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblIdR.setText("0.000");
        jLblIdR.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblIdR);
        jLblIdR.setBounds(230, 10, 120, 40);

        jLblOdR.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblOdR.setForeground(new java.awt.Color(0, 0, 0));
        jLblOdR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblOdR.setText("0.000");
        jLblOdR.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblOdR);
        jLblOdR.setBounds(125, 240, 110, 40);

        jLblMainL.setFont(new java.awt.Font("DialogInput", 3, 32)); // NOI18N
        jLblMainL.setForeground(new java.awt.Color(0, 0, 0));
        jLblMainL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblMainL.setText("0.000");
        jLblMainL.setPreferredSize(new java.awt.Dimension(94, 40));
        jPanel1.add(jLblMainL);
        jLblMainL.setBounds(20, 30, 120, 40);

        jLabelMainImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rezba_0/pkg01/images/OD.png"))); // NOI18N
        jLabelMainImg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelMainImg.setName(""); // NOI18N
        jPanel1.add(jLabelMainImg);
        jLabelMainImg.setBounds(0, 0, 550, 400);

        javax.swing.GroupLayout jPanelImageLayout = new javax.swing.GroupLayout(jPanelImage);
        jPanelImage.setLayout(jPanelImageLayout);
        jPanelImageLayout.setHorizontalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        jPanelImageLayout.setVerticalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        jPanelItemSelectiont.setMaximumSize(new java.awt.Dimension(150, 500));
        jPanelItemSelectiont.setMinimumSize(new java.awt.Dimension(150, 130));
        jPanelItemSelectiont.setName(""); // NOI18N
        jPanelItemSelectiont.setPreferredSize(new java.awt.Dimension(150, 128));

        jRadioOD.setSelected(true);
        jRadioOD.setText("БОЛТ");
        jRadioOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioODActionPerformed(evt);
            }
        });

        jRadioID.setText("ГАЙКА");
        jRadioID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioIDActionPerformed(evt);
            }
        });

        jComboMetricThread.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboMetricThread.setMaximumRowCount(10);
        jComboMetricThread.setModel(new javax.swing.DefaultComboBoxModel<>(odD1));
        jComboMetricThread.setSelectedIndex(27);
        jComboMetricThread.setToolTipText("Виберіть діаметр різьби");
        jComboMetricThread.setAlignmentX(0.1F);
        jComboMetricThread.setAlignmentY(0.1F);
        jComboMetricThread.setMaximumSize(null);
        jComboMetricThread.setMinimumSize(null);
        jComboMetricThread.setName(""); // NOI18N
        jComboMetricThread.setPreferredSize(null);
        jComboMetricThread.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboMetricThreadItemStateChanged(evt);
            }
        });
        jComboMetricThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMetricThreadActionPerformed(evt);
            }
        });

        jComboMetricSteps.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboMetricSteps.setMaximumRowCount(10);
        jComboMetricSteps.setModel(new javax.swing.DefaultComboBoxModel<>(stepsTo11_2));
        jComboMetricSteps.setSelectedIndex(4);
        jComboMetricSteps.setToolTipText("Виберіть крок різьби");
        jComboMetricSteps.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboMetricStepsItemStateChanged(evt);
            }
        });
        jComboMetricSteps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMetricStepsActionPerformed(evt);
            }
        });

        jComboMetricTolerance1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboMetricTolerance1.setMaximumRowCount(10);
        jComboMetricTolerance1.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
        jComboMetricTolerance1.setToolTipText("Виберіть допуск");
        jComboMetricTolerance1.setLightWeightPopupEnabled(false);
        jComboMetricTolerance1.setMaximumSize(null);
        jComboMetricTolerance1.setMinimumSize(null);
        jComboMetricTolerance1.setPreferredSize(null);
        jComboMetricTolerance1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboMetricTolerance1ItemStateChanged(evt);
            }
        });
        jComboMetricTolerance1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMetricTolerance1ActionPerformed(evt);
            }
        });

        jComboRadius.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboRadius.setModel(new javax.swing.DefaultComboBoxModel<>(Radius));
        jComboRadius.setToolTipText("Виберіть радіус впадини");
        jComboRadius.setEnabled(false);
        jComboRadius.setMaximumSize(null);
        jComboRadius.setMinimumSize(null);
        jComboRadius.setPreferredSize(null);
        jComboRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboRadiusActionPerformed(evt);
            }
        });

        jComboMetricTolerance2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboMetricTolerance2.setMaximumRowCount(10);
        jComboMetricTolerance2.setModel(new javax.swing.DefaultComboBoxModel<>(Tolerance));
        jComboMetricTolerance2.setToolTipText("Виберіть другий допуск");
        jComboMetricTolerance2.setEnabled(false);
        jComboMetricTolerance2.setLightWeightPopupEnabled(false);
        jComboMetricTolerance2.setMaximumSize(null);
        jComboMetricTolerance2.setMinimumSize(null);
        jComboMetricTolerance2.setPreferredSize(null);
        jComboMetricTolerance2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboMetricTolerance2ItemStateChanged(evt);
            }
        });
        jComboMetricTolerance2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMetricTolerance2ActionPerformed(evt);
            }
        });

        jCheckSecondTolerance.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckSecondTolerance.setName(""); // NOI18N
        jCheckSecondTolerance.setPreferredSize(null);
        jCheckSecondTolerance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckSecondToleranceActionPerformed(evt);
            }
        });

        jCheckBoxRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRadiusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelItemSelectiontLayout = new javax.swing.GroupLayout(jPanelItemSelectiont);
        jPanelItemSelectiont.setLayout(jPanelItemSelectiontLayout);
        jPanelItemSelectiontLayout.setHorizontalGroup(
            jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jRadioOD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jRadioID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                        .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jCheckBoxRadius))
                            .addComponent(jCheckSecondTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                                .addComponent(jComboMetricTolerance2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jComboMetricTolerance1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanelItemSelectiontLayout.createSequentialGroup()
                        .addComponent(jComboMetricThread, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jComboMetricSteps, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        jPanelItemSelectiontLayout.setVerticalGroup(
            jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelItemSelectiontLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioOD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboMetricThread, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboMetricSteps, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboMetricTolerance2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboMetricTolerance1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckSecondTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelItemSelectiontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelResultOutput.setMaximumSize(new java.awt.Dimension(150, 600));
        jPanelResultOutput.setMinimumSize(new java.awt.Dimension(150, 250));
        jPanelResultOutput.setName(""); // NOI18N
        jPanelResultOutput.setPreferredSize(new java.awt.Dimension(150, 250));
        jPanelResultOutput.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Зовнішній діаметр");
        jLabel3.setAlignmentY(0.0F);
        jLabel3.setMaximumSize(new java.awt.Dimension(140, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(140, 20));
        jLabel3.setName(""); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 20));
        jLabel3.setRequestFocusEnabled(false);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel3, gridBagConstraints);
        jLabel3.getAccessibleContext().setAccessibleName("");
        jLabel3.getAccessibleContext().setAccessibleDescription("");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("MAX");
        jLabel4.setAlignmentY(0.0F);
        jLabel4.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel4.setName(""); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel4, gridBagConstraints);
        jLabel4.getAccessibleContext().setAccessibleName("");
        jLabel4.getAccessibleContext().setAccessibleDescription("");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("jLable5");
        jLabel5.setToolTipText("");
        jLabel5.setAlignmentY(0.0F);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel5.setName(""); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel5, gridBagConstraints);
        jLabel5.getAccessibleContext().setAccessibleName("");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("MID");
        jLabel6.setAlignmentY(0.0F);
        jLabel6.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel6.setName(""); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel6.setRequestFocusEnabled(false);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel6, gridBagConstraints);
        jLabel6.getAccessibleContext().setAccessibleName("");
        jLabel6.getAccessibleContext().setAccessibleDescription("");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("jLable7");
        jLabel7.setToolTipText("");
        jLabel7.setAlignmentY(0.0F);
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel7.setName(""); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel7.setRequestFocusEnabled(false);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel7, gridBagConstraints);
        jLabel7.getAccessibleContext().setAccessibleName("");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("MIN");
        jLabel8.setAlignmentY(0.0F);
        jLabel8.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel8.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel8.setName(""); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel8.setRequestFocusEnabled(false);
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel8, gridBagConstraints);
        jLabel8.getAccessibleContext().setAccessibleName("");
        jLabel8.getAccessibleContext().setAccessibleDescription("");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("jLable9");
        jLabel9.setToolTipText("");
        jLabel9.setAlignmentY(0.0F);
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel9.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel9.setName(""); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel9.setRequestFocusEnabled(false);
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel9, gridBagConstraints);
        jLabel9.getAccessibleContext().setAccessibleName("");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Середній діаметр");
        jLabel10.setAlignmentY(0.0F);
        jLabel10.setMaximumSize(new java.awt.Dimension(140, 20));
        jLabel10.setMinimumSize(new java.awt.Dimension(140, 20));
        jLabel10.setName(""); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(140, 20));
        jLabel10.setRequestFocusEnabled(false);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel10, gridBagConstraints);
        jLabel10.getAccessibleContext().setAccessibleName("");
        jLabel10.getAccessibleContext().setAccessibleDescription("");

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setText("MAX");
        jLabel11.setAlignmentY(0.0F);
        jLabel11.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel11.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel11.setName(""); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel11.setRequestFocusEnabled(false);
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel11, gridBagConstraints);
        jLabel11.getAccessibleContext().setAccessibleName("");
        jLabel11.getAccessibleContext().setAccessibleDescription("");

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("jLable12");
        jLabel12.setToolTipText("");
        jLabel12.setAlignmentY(0.0F);
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel12.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel12.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel12.setName(""); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel12.setRequestFocusEnabled(false);
        jLabel12.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel12, gridBagConstraints);
        jLabel12.getAccessibleContext().setAccessibleName("");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setText("MID");
        jLabel13.setToolTipText("");
        jLabel13.setAlignmentY(0.0F);
        jLabel13.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel13.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel13.setName(""); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel13.setRequestFocusEnabled(false);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel13, gridBagConstraints);
        jLabel13.getAccessibleContext().setAccessibleName("");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("jLable14");
        jLabel14.setToolTipText("");
        jLabel14.setAlignmentY(0.0F);
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel14.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel14.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel14.setName(""); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel14.setRequestFocusEnabled(false);
        jLabel14.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel14, gridBagConstraints);
        jLabel14.getAccessibleContext().setAccessibleName("");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel15.setText("MIN");
        jLabel15.setAlignmentY(0.0F);
        jLabel15.setMaximumSize(new java.awt.Dimension(65, 20));
        jLabel15.setMinimumSize(new java.awt.Dimension(65, 20));
        jLabel15.setName(""); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel15.setRequestFocusEnabled(false);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel15, gridBagConstraints);
        jLabel15.getAccessibleContext().setAccessibleName("");
        jLabel15.getAccessibleContext().setAccessibleDescription("");

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("jLable16");
        jLabel16.setToolTipText("");
        jLabel16.setAlignmentY(0.0F);
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel16.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel16.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel16.setName(""); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 20));
        jLabel16.setRequestFocusEnabled(false);
        jLabel16.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel16, gridBagConstraints);
        jLabel16.getAccessibleContext().setAccessibleName("");

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("jLabel17");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel17, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(12, 99, 23));
        jLabel20.setText("jLabel20");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanelResultOutput.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(12, 99, 23));
        jLabel21.setText("jLabel21");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanelResultOutput.add(jLabel21, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(12, 99, 23));
        jLabel22.setText("jLabel22");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanelResultOutput.add(jLabel22, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(12, 99, 23));
        jLabel23.setText("jLabel23");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanelResultOutput.add(jLabel23, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("jLabel24");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jPanelResultOutput.add(jLabel24, gridBagConstraints);

        javax.swing.GroupLayout jPanelMainTab1Layout = new javax.swing.GroupLayout(jPanelMainTab1);
        jPanelMainTab1.setLayout(jPanelMainTab1Layout);
        jPanelMainTab1Layout.setHorizontalGroup(
            jPanelMainTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainTab1Layout.createSequentialGroup()
                .addComponent(jPanelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelMainTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelItemSelectiont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelResultOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMainTab1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMainTab1Layout.setVerticalGroup(
            jPanelMainTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainTab1Layout.createSequentialGroup()
                .addGroup(jPanelMainTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainTab1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelItemSelectiont, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanelResultOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMainTab1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab3.addTab("Метрична", null, jPanelMainTab1, "Мертрична різьба");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Скоро...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTab3.addTab("Дюймова", null, jPanel2, "Скоро...");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Скоро...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTab3.addTab("Трапеціїдальна", null, jPanel3, "Скоро...");

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel30.setText("Автор: Дмитро Лемеш");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel30, gridBagConstraints);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel31.setText("Запитання та пропозиції?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jLabel31, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel27.setText("Пишіть на: hizz@ukr.net");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel5.add(jLabel27, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel28.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel5.add(jLabel28, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel29.setText("v0.04.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel5.add(jLabel29, gridBagConstraints);

        jTab3.addTab("about", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioIDActionPerformed
        setOdSelected(false);
        updateMainImg("ID");
        jLblIdR.setVisible(true);
        jLblOdR.setVisible(false);
        setToleransesIn1();
        setToleransesIn2();
        refresMetrics();
    }//GEN-LAST:event_jRadioIDActionPerformed

    private void jRadioODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioODActionPerformed
        setOdSelected(true);
        updateMainImg("OD");
        jLblIdR.setVisible(false);
        jLblOdR.setVisible(true);
        setToleransesIn1();
        setToleransesIn2();
        refresMetrics();
    }//GEN-LAST:event_jRadioODActionPerformed

    private void jComboMetricTolerance1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMetricTolerance1ActionPerformed
        setTextToMainLbl();
        ReadWriteToleranses();
    }//GEN-LAST:event_jComboMetricTolerance1ActionPerformed

    private void jComboMetricTolerance1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboMetricTolerance1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboMetricTolerance1ItemStateChanged

    private void jComboMetricStepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMetricStepsActionPerformed
        refresMetrics();
        ReadWriteToleranses();
        //setValidToleranses();
        //setDefaultStepForThread();
        //setTextToMainLbl();
        //sumDiamPlusSteps();  //тимчасово
        //jLabel6.setText(Integer.toString(jComboMetricSteps.getSelectedIndex()));
    }//GEN-LAST:event_jComboMetricStepsActionPerformed

    private void jComboMetricStepsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboMetricStepsItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboMetricStepsItemStateChanged

    private void jComboMetricThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMetricThreadActionPerformed
        refresMetrics();
        setValidStepsForDiam();
        setDefaultStepForThread();
        ReadWriteToleranses();
        //setTextToMainLbl();
        //setValidToleranses();
        //sumDiamPlusSteps(); //тимчасово
        //jLabel6.setText(Integer.toString(jComboMetricThread.getSelectedIndex()));

    }//GEN-LAST:event_jComboMetricThreadActionPerformed

    private void jComboMetricThreadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboMetricThreadItemStateChanged

    }//GEN-LAST:event_jComboMetricThreadItemStateChanged

    private void jComboMetricTolerance2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboMetricTolerance2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboMetricTolerance2ItemStateChanged

    private void jComboMetricTolerance2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMetricTolerance2ActionPerformed
        //refresMetrics();
        setTextToMainLbl();
        ReadWriteToleranses();

    }//GEN-LAST:event_jComboMetricTolerance2ActionPerformed

    private void jCheckSecondToleranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckSecondToleranceActionPerformed
        setTextToMainLbl();
        if(jCheckSecondTolerance.isSelected())
            jComboMetricTolerance2.setEnabled(true);
        else jComboMetricTolerance2.setEnabled(false);
    }//GEN-LAST:event_jCheckSecondToleranceActionPerformed

    private void jComboRadiusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboRadiusActionPerformed
        refresMetrics();
    }//GEN-LAST:event_jComboRadiusActionPerformed

    private void jCheckBoxRadiusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRadiusActionPerformed
        //jComboRadius.setEnabled(true);
        if(jCheckBoxRadius.isSelected())
            jComboRadius.setEnabled(true);
        else jComboRadius.setEnabled(false);
        refresMetrics();
    }//GEN-LAST:event_jCheckBoxRadiusActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Settings.Save();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command lineInTable arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox jCheckBoxRadius;
    public static javax.swing.JCheckBox jCheckSecondTolerance;
    public static javax.swing.JComboBox<String> jComboMetricSteps;
    public static javax.swing.JComboBox<String> jComboMetricThread;
    public static javax.swing.JComboBox<String> jComboMetricTolerance1;
    public static javax.swing.JComboBox<String> jComboMetricTolerance2;
    public static javax.swing.JComboBox<String> jComboRadius;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMainImg;
    private javax.swing.JLabel jLblD;
    private javax.swing.JLabel jLblD1;
    private javax.swing.JLabel jLblD2;
    private javax.swing.JLabel jLblIdR;
    private javax.swing.JLabel jLblMainL;
    private javax.swing.JLabel jLblMainP;
    private javax.swing.JLabel jLblOdR;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelImage;
    private javax.swing.JPanel jPanelItemSelectiont;
    private javax.swing.JPanel jPanelMainTab1;
    private javax.swing.JPanel jPanelResultOutput;
    public static javax.swing.JRadioButton jRadioID;
    public static javax.swing.JRadioButton jRadioOD;
    private javax.swing.JTabbedPane jTab3;
    // End of variables declaration//GEN-END:variables
}
