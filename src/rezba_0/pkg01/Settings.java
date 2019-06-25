package rezba_0.pkg01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * current date: 26.09.17 18:39
 *
 * @author HizZ
 */
public class Settings implements Serializable{

    protected String mainText    ; // = "...."
    private boolean OD_ID        ; // = true;
    private String threadVal     ; //  = "M 6";
    private String stepVal       ; // = "1";
    private String tol1Val       ; // = "6g";
    private boolean checkTol2    ; // = true;
    private String tol2Val       ; // = "5g";
    private boolean checkRadius  ; // = true;
    private String radiusVal     ; // = "0.17";
    
    
    public static void Restore() {
       
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream ("settings.bin"));
            
            Settings settingsRestore = (Settings) objectIn.readObject();
            
            MainFrame.defaultYear = settingsRestore.mainText;
            
            if((boolean)settingsRestore.OD_ID == true) MainFrame.jRadioOD.doClick();
            else MainFrame.jRadioID.doClick();
            MainFrame.jComboMetricThread.setSelectedItem((String)settingsRestore.threadVal);
            MainFrame.jComboMetricSteps.setSelectedItem((String)settingsRestore.stepVal);
            MainFrame.jComboMetricTolerance1.setSelectedItem((String)settingsRestore.tol1Val);
            if((boolean)settingsRestore.checkTol2 == true){
                MainFrame.jCheckSecondTolerance.doClick();
                MainFrame.jComboMetricTolerance2.setSelectedItem((String)settingsRestore.tol2Val);
            }
            if ((boolean)settingsRestore.checkRadius == true){
                MainFrame.jCheckBoxRadius.doClick();
                MainFrame.jComboRadius.setSelectedItem((String)settingsRestore.radiusVal);
            }
        
        } catch (IOException ex) {
            System.err.println("Reading Setting Error!!!");
            //Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("Problem with exeption");
            //Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Save(){
        
        Settings settingsSeve = new Settings();
        
        settingsSeve.mainText = MainFrame.defaultYear;
        settingsSeve.OD_ID = (MainFrame.jRadioOD.isSelected());
        settingsSeve.threadVal = (String) MainFrame.jComboMetricThread.getSelectedItem();
        settingsSeve.stepVal = (String) MainFrame.jComboMetricSteps.getSelectedItem();
        settingsSeve.tol1Val = (String) MainFrame.jComboMetricTolerance1.getSelectedItem();
        settingsSeve.checkTol2 = MainFrame.jCheckSecondTolerance.isSelected();
        settingsSeve.tol2Val = (String) MainFrame.jComboMetricTolerance2.getSelectedItem();
        settingsSeve.checkRadius = MainFrame.jCheckBoxRadius.isSelected();
        settingsSeve.radiusVal = (String) MainFrame.jComboRadius.getSelectedItem();

        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("settings.bin"));
            System.err.println("Saving");
            objectOut.writeObject(settingsSeve);
            objectOut.close();
            System.err.println("Saved!!!");
            
        } catch (IOException iOException) {
            System.err.println("IOExeption in ''Save()''");
        }

    }
}
