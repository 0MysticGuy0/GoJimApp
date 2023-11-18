package com.mygy.gojimapp.data;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class User implements Serializable {
    private String name = "Супер Имя";
    private Date birthDate;
    private Uri iconUri;
    private String email;
    private String dockId;
    private transient String password;
    private ArrayList<TrainingProgramm> favPrograms = new ArrayList<>();
    private static FirebaseFirestore usersBase;
    private HashMap<String, Object> userDocument = new HashMap<>(); // this will be saved to database
    public static SimpleDateFormat dateForamt;

    static {
        usersBase = FirebaseFirestore.getInstance();
        dateForamt = new SimpleDateFormat("dd.MM.yyyy");
    }

    private void checkForBaseParameters() {
        if (ProgressParameter.getBodyParameterByName("вес") == null) {
            new ProgressParameter("вес", ProgressParameter.Group.BODY_PARAMETERS,
                    new ProgressParameter.ParameterInfo("вес", "кг"));
        }
        if (ProgressParameter.getBodyParameterByName("рост") == null) {
            new ProgressParameter("рост", ProgressParameter.Group.BODY_PARAMETERS,
                    new ProgressParameter.ParameterInfo("рост", "см"));
        }
        if (ProgressParameter.getBodyParameterByName("Обхват талии") == null) {
            new ProgressParameter("Обхват талии", ProgressParameter.Group.BODY_PARAMETERS,
                    new ProgressParameter.ParameterInfo("обхват талии", "см"));
        }
        if (ProgressParameter.getOtherParameterByName("Килокалории") == null) {
            new ProgressParameter("Килокалории", ProgressParameter.Group.OTHER,
                    new ProgressParameter.ParameterInfo("Употребил", "Ккал"));
        }
    }

    public User() {
        checkForBaseParameters();
        updateAllDataInDoc();
    }

    public User(HashMap<String, Object> userDocument) {
        name = (String) userDocument.get(Constants.USER_NAME);
        email = (String) userDocument.get(Constants.USER_EMAIL);
        dockId = (String) userDocument.get(Constants.USER_DOCK_ID);
        try {
            birthDate = ((Timestamp) userDocument.get(Constants.USER_BIRTHDATE)).toDate();
        } catch (NullPointerException ex) {
            birthDate = new Date();
        }
        try {
            iconUri = Uri.parse((String) userDocument.get(Constants.USER_ICO_URI));
        } catch (NullPointerException ex) {
            iconUri = null;
        }

        try {
            ArrayList<String> favProgsNames = (ArrayList<String>) userDocument.get(Constants.USER_FAV_PROGRAMS);
            System.out.println("--------loaded favProgs");
            for (String n : favProgsNames) {
                System.out.println(n);
                TrainingProgramm tp = TrainingProgramm.getTrainingProgramByName(n);
                if (tp != null) {
                    favPrograms.add(tp);
                }
            }
            System.out.println("--------");
        } catch (NullPointerException ex) {
            System.out.println("!!!!!cant get favprogs from base!!!!!!");
        }

        try {
            List<HashMap<String, Object>> bodyParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_BODY_PARAMS);
            for (HashMap<String, Object> p : bodyParams) {
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.BODY_PARAMETERS);
            }
            List<HashMap<String, Object>> exerciseParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_EXERCISES_PARAMS);
            for (HashMap<String, Object> p : exerciseParams) {
                System.out.println("\n" + p);
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.EXERCISES);
            }
            List<HashMap<String, Object>> otherParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_OTHER_PARAMS);
            for (HashMap<String, Object> p : otherParams) {
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.OTHER);
            }
            checkForBaseParameters();
        } catch (NullPointerException ex) {
            System.out.println("!!!!!cant get parameters from base!!!!!!");
        }
        updateAllDataInDoc();
    }

    /////////////////////////////////////////----------------------------------------

    public void updateAllDataInDoc() {
        userDocument.put(Constants.USER_NAME, name);
        userDocument.put(Constants.USER_EMAIL, email);
        userDocument.put(Constants.USER_BIRTHDATE, birthDate);
        if (iconUri != null)
            userDocument.put(Constants.USER_ICO_URI, iconUri.toString());
        userDocument.put(Constants.USER_DOCK_ID, dockId);

        updateParametersInDoc(ProgressParameter.Group.BODY_PARAMETERS);
        updateParametersInDoc(ProgressParameter.Group.EXERCISES);
        updateParametersInDoc(ProgressParameter.Group.OTHER);
        updateFavProgramInDoc();
    }

    public void updateFavProgramInDoc() {
        ArrayList<String> fav = new ArrayList<>();
        for (TrainingProgramm p : favPrograms) {
            fav.add(p.getName());
        }
        userDocument.put(Constants.USER_FAV_PROGRAMS, fav);
        saveDataToBase();
    }

    public void updateParametersInDoc(ProgressParameter.Group group) {
        switch (group) {
            case EXERCISES:
                userDocument.put(Constants.USER_ALL_EXERCISES_PARAMS, ProgressParameter.allExercisesParameters);
                break;
            case OTHER:
                userDocument.put(Constants.USER_ALL_OTHER_PARAMS, ProgressParameter.allOtherParameters);
                break;
            case BODY_PARAMETERS:
                userDocument.put(Constants.USER_ALL_BODY_PARAMS, ProgressParameter.allBodyParameters);
                break;
        }
        saveDataToBase();
    }

    /////////////////////////////////////////----------------------------------------

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, Object> getUserDocument() {
        return userDocument;
    }

    public Uri getIconUri() {
        return iconUri;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFormattedBirthDate() {
        if (birthDate == null) return "00.00.0000";
        return dateForamt.format(birthDate);
    }
    public String getDockId() {
        return dockId;
    }

    public ArrayList<TrainingProgramm> getFavPrograms() {
        return favPrograms;
    }

    /////////////////////////////////////////----------------------------------------

    public void setDockId(String dockId) {
        this.dockId = dockId;
        userDocument.put(Constants.USER_DOCK_ID, dockId);
        saveDataToBase();
    }

    public void setEmail(String email) {
        this.email = email;
        userDocument.put(Constants.USER_EMAIL, email);
        saveDataToBase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIconUri(Uri iconUri) {
        this.iconUri = iconUri;
        userDocument.put(Constants.USER_ICO_URI, iconUri.toString());
        saveDataToBase();
    }

    public void setIconUri(String iconUri) {
        this.iconUri = Uri.parse(iconUri);
        userDocument.put(Constants.USER_ICO_URI, iconUri);
        saveDataToBase();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        userDocument.put(Constants.USER_BIRTHDATE, birthDate);
        saveDataToBase();
    }

    public void setName(String name) {
        this.name = name;
        userDocument.put(Constants.USER_NAME, name);
        saveDataToBase();
    }

    public void addFavProgram(TrainingProgramm program) {
        favPrograms.add(program);
        updateFavProgramInDoc();
    }

    /////////////////////////////////////////----------------------------------------

    public void saveDataToBase() {
        if (dockId != null) {
            usersBase.collection(Constants.USERS_BASE)
                    .document(this.getDockId())
                    .update(this.getUserDocument())
                    .addOnSuccessListener(docRef -> {
                        System.out.println("----------Сохранил пользователя" + this.getEmail() + "-----------");

                    })
                    .addOnFailureListener(ex -> {
                        System.out.println("----------Ошибка сохранения" + this.getEmail() + "-----------");
                    });
        }
    }
    public void signOut(Context context){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        try (FileOutputStream fos = context.openFileOutput(Constants.LOGIN_FILE_NAME, Context.MODE_PRIVATE)) {
            Properties properties = new Properties();
            properties.setProperty(Constants.USER_EMAIL, "");
            properties.setProperty(Constants.USER_PASSWORD, "");
            properties.store(fos, "login data");
            System.out.println("\n======================================REMOVED LOGIN DATA=======================================\n");
        } catch (FileNotFoundException ex) {
            System.out.println("!!!!!!!!!!!!!!!\nNO FILE TO REMOVE LOGIN DATA!\n!!!!!!!!!!!!!!!!!");
            throw new RuntimeException();
        } catch (IOException ex) {
            System.out.println("ERROR REMOVING LOGIN DATA!!!!!!!!!!!!!");
            throw new RuntimeException();
        }
    }

    public void saveLoginDataToFile(Context context) {
        try (FileOutputStream fos = context.openFileOutput(Constants.LOGIN_FILE_NAME, Context.MODE_PRIVATE)) {
            Properties properties = new Properties();
            properties.setProperty(Constants.USER_EMAIL, email);
            properties.setProperty(Constants.USER_PASSWORD, password);
            properties.store(fos, "login data");
            System.out.println("\n======================================SAVED LOGIN DATA=======================================\n");
        } catch (FileNotFoundException ex) {
            System.out.println("!!!!!!!!!!!!!!!\nNO FILE TO SAVE LOGIN DATA!\n!!!!!!!!!!!!!!!!!");
            throw new RuntimeException();
        } catch (IOException ex) {
            System.out.println("ERROR SAVING LOGIN DATA!!!!!!!!!!!!!");
            throw new RuntimeException();
        }
    }

    public static String[] readLoginDataFromFile(Context context) {
        try(FileInputStream fis = context.openFileInput(Constants.LOGIN_FILE_NAME)){
            Properties properties = new Properties();
            properties.load(fis);
            String[] data = new String[2];
            data[0] = properties.getProperty(Constants.USER_EMAIL);
            data[1] = properties.getProperty(Constants.USER_PASSWORD);
            return data;
        } catch (FileNotFoundException ex) {
            return  null;
        } catch (IOException ex) {
            System.out.println("ERROR READING LOGIN DATA!!!!!!!!!!!!!\n"+ex.getMessage());
            throw new RuntimeException();
        }
    }
}
