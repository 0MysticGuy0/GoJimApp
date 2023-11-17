package com.mygy.gojimapp.data;

import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {
    private String name = "Супер Имя";
    private Date birthDate;
    private Uri iconUri;
    private String email;
    private String dockId;
    private ArrayList<TrainingProgramm> favPrograms = new ArrayList<>();
    private static FirebaseFirestore usersBase;
    private HashMap<String, Object> userDocument = new HashMap<>(); // this will be saved to database
    private int age = 18;
    public static SimpleDateFormat dateForamt;

    static {
        usersBase = FirebaseFirestore.getInstance();
        dateForamt = new SimpleDateFormat("dd.MM.yyyy");
    }

    public User() {
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
        }catch (NullPointerException ex){
            System.out.println("!!!!!cant get favprogs from base!!!!!!");
        }

        try {
            List<HashMap<String, Object>> bodyParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_BODY_PARAMS);
            for (HashMap<String, Object> p : bodyParams) {
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.BODY_PARAMETERS);
            }
            List<HashMap<String, Object>> exerciseParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_EXERCISES_PARAMS);
            for (HashMap<String, Object> p : exerciseParams) {
                System.out.println("\n"+p);
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.EXERCISES);
            }
            List<HashMap<String, Object>> otherParams = (List<HashMap<String, Object>>) userDocument.get(Constants.USER_ALL_OTHER_PARAMS);
            for (HashMap<String, Object> p : otherParams) {
                ProgressParameter.getParameterFromDoc(p, ProgressParameter.Group.OTHER);
            }
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
    public HashMap<String, Object> getUserDocument() {
        return userDocument;
    }
    public Uri getIconUri() {
        return iconUri;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public String getFormattedBirthDate(){
        if(birthDate == null) return "00.00.0000";
        return dateForamt.format(birthDate);
    }
    public int getAge() {
        return age;
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
}
