package com.mygy.gojimapp.data;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.data.Exercise.MuscleGroup;

public class Store {

    public static final Exercise hyperextension;
    public static final Exercise captainsChairLegRaise;
    public static final Exercise declineSitUps;
    public static final Exercise benchPress;
    public static final Exercise dumbbellBenchPress;
    public static final Exercise dumbbellFly;
    public static final Exercise squat;
    public static final Exercise barbellCurl;
    public static final Exercise pullUp;
    public static final Exercise narrowGripPullup;
    public static final Exercise wideGripPullup;
    public static final Exercise dips;
    public static final Exercise barbellBentOverRow;
    public static final Exercise legPress;
    public static final Exercise romanianDeadlift;
    public static final Exercise barbellOverheadPress;
    public static final Exercise dumbbellShoulderPress;
    public static final Exercise dumbbellLateralRaise;
    public static final Exercise cableSeatedRow;
    public static final Exercise dumbbellPullover;
    public static final Exercise dumbbellScullCrusher;
    public static final Exercise pushDown;
    public static final Exercise hammerCurl;
    public static final Exercise seatedInclineDumbbellCurl;

    public static final TrainingProgramm program01;
    public static final TrainingProgramm program02;


    static {
        squat = new Exercise("Присед со штангой","3-5","6-10", R.drawable.squat,new MuscleGroup[]{MuscleGroup.LEGS});
        legPress = new Exercise("Жим ногами в тренажёре","3-4","8-12", R.drawable.leg_press,new MuscleGroup[]{MuscleGroup.LEGS});
        romanianDeadlift = new Exercise("Становая тяга на прямых ногах","3-4","8-12", R.drawable.romanian_deadlift,new MuscleGroup[]{MuscleGroup.LEGS,MuscleGroup.BODY});

        hyperextension = new Exercise("Гиперэкстензия","3","10-15", R.drawable.hyperextension,new MuscleGroup[]{MuscleGroup.BODY});
        benchPress = new Exercise("Жим штанги лёжа","3-5","6-10", R.drawable.bench_press,new MuscleGroup[]{MuscleGroup.BODY});
        wideGripPullup = new Exercise("Подтягивания широким хватом к груди","3-4","6-15",R.drawable.pullup,new MuscleGroup[]{MuscleGroup.BODY});
        dumbbellBenchPress = new Exercise("Жим гантелей лёжа под углом","3","8-12", R.drawable.dumbbell_bench_press,new MuscleGroup[]{MuscleGroup.BODY});
        captainsChairLegRaise = new Exercise("Подъём ног в упоре","3","12-20", R.drawable.captains_chair_leg_raise,new MuscleGroup[]{MuscleGroup.BODY});
        cableSeatedRow = new Exercise("Тяга горизонтального блока","3","8-12", R.drawable.cable_seated_row,new MuscleGroup[]{MuscleGroup.BODY});
        dumbbellPullover = new Exercise("Пуловер с гантелей лёжа","3","10-15", R.drawable.dumbbell_pullover,new MuscleGroup[]{MuscleGroup.BODY});
        declineSitUps = new Exercise("Скручивания на наклонной скамье","3","12-18", R.drawable.decline_situps,new MuscleGroup[]{MuscleGroup.BODY});
        dumbbellFly = new Exercise("Разводки с гантелями лёжа под углом","3","10-15", R.drawable.dumbbell_fly,new MuscleGroup[]{MuscleGroup.BODY});
        barbellBentOverRow = new Exercise("Тяга штанги в наклоне","3-4","6-10", R.drawable.barbell_bent_over_row,new MuscleGroup[]{MuscleGroup.BODY});
        pullUp = new Exercise("Подтягивания","3","6-12", R.drawable.pullup,new MuscleGroup[]{MuscleGroup.BODY,MuscleGroup.ARMS});

        barbellCurl = new Exercise("Подъем штанги на бицепс","3","10-15", R.drawable.barbell_curl,new MuscleGroup[]{MuscleGroup.ARMS});
        narrowGripPullup = new Exercise("Подтягивания обратным хватом","3-4","6-15",R.drawable.chin_up,new MuscleGroup[]{MuscleGroup.ARMS});
        dips = new Exercise("Отжимания от брусьев","3-4","6-15", R.drawable.triceps_dips,new MuscleGroup[]{MuscleGroup.ARMS,MuscleGroup.BODY});
        barbellOverheadPress = new Exercise("Жим штанги с груди стоя","3-4","6-10", R.drawable.barbell_overhead_press,new MuscleGroup[]{MuscleGroup.ARMS});
        dumbbellShoulderPress = new Exercise("Жим гантелей сидя","3","8-12", R.drawable.dumbbell_shoulder_press,new MuscleGroup[]{MuscleGroup.ARMS});
        dumbbellLateralRaise = new Exercise("Махи гантелями в стороны","3","10-15", R.drawable.dumbbell_lateral_raise,new MuscleGroup[]{MuscleGroup.ARMS});
        dumbbellScullCrusher = new Exercise("Французский жим с гантелями лёжа","3","10-15", R.drawable.dumbbell_scull_crusher,new MuscleGroup[]{MuscleGroup.ARMS});
        pushDown = new Exercise("Разгибание рук с верхнего блока","3","10-15", R.drawable.pushdown,new MuscleGroup[]{MuscleGroup.ARMS});
        hammerCurl = new Exercise("Сгибание рук с гантелями \"молот\"","3","10-15", R.drawable.dumbbell_hammer_curl,new MuscleGroup[]{MuscleGroup.ARMS});
        seatedInclineDumbbellCurl = new Exercise("Сгибание рук с гантелями сидя под углом","3","8-12", R.drawable.seated_incline_dumbbell_curl,new MuscleGroup[]{MuscleGroup.ARMS});

        program01=new TrainingProgramm("Базовый комплекс упражнений для мужчин на массу и силу",
                new TrainingDay("Ноги и печи",hyperextension,squat,legPress,romanianDeadlift,barbellOverheadPress,dumbbellShoulderPress,dumbbellLateralRaise),
                new TrainingDay("Спина и грудь", captainsChairLegRaise, benchPress,wideGripPullup,dumbbellBenchPress,dumbbellFly,barbellBentOverRow,cableSeatedRow,dumbbellPullover),
                new TrainingDay("Бицепс и трицепс", hyperextension,declineSitUps,dips,dumbbellScullCrusher,pushDown,narrowGripPullup,hammerCurl,seatedInclineDumbbellCurl)
        );
        program02 = new TrainingProgramm("Тренировка на повышение жима лёжа",
                new TrainingDay("Ноги и печи",hyperextension,squat,legPress,romanianDeadlift,barbellOverheadPress,dumbbellShoulderPress,dumbbellLateralRaise),
                new TrainingDay("Спина и грудь", captainsChairLegRaise, benchPress,wideGripPullup,dumbbellBenchPress,dumbbellFly,barbellBentOverRow,cableSeatedRow,dumbbellPullover),
                new TrainingDay("Бицепс и трицепс", hyperextension,declineSitUps,dips,dumbbellScullCrusher,pushDown,narrowGripPullup,hammerCurl,seatedInclineDumbbellCurl)
        );
    }
}
