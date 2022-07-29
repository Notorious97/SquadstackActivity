package com.squadstack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class FileParser {
    private HashMap<String, Method> commands = new HashMap();
    static ParkingLot parkingLot;

    public FileParser() {
        parkingLot = new ParkingLot();

        try {
            this.fillcommands();
        } catch (NoSuchMethodException var2) {
            var2.printStackTrace();
        }

    }

    public void processLine(String line) {
        String[] inputs = line.split(" ");
        Method method;
        switch(inputs.length) {
            case 2:
                try {
                    method = (Method)this.commands.get(inputs[0]);
                    if (method != null) {
                        method.invoke(parkingLot, inputs[1]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (IllegalAccessException var6) {
                    var6.printStackTrace();
                } catch (InvocationTargetException var7) {
                    var7.printStackTrace();
                }
                break;
            case 4:
                try {
                    method = (Method)this.commands.get(inputs[0]);
                    if (method != null) {
                        method.invoke(parkingLot, inputs[1], inputs[3]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (IllegalAccessException var4) {
                    var4.printStackTrace();
                } catch (InvocationTargetException var5) {
                    var5.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid input");
        }

    }

    public void processFile(String path) {
        File file = new File(path);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            try {
                while((line = br.readLine()) != null) {
                    this.processLine(line.trim());
                }
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        }

    }

    private void fillcommands() throws NoSuchMethodException {
        this.commands.put("Create_parking_lot", ParkingLot.class.getMethod("initParkingLot", String.class));
        this.commands.put("Park", ParkingLot.class.getMethod("parkVehicle", String.class, String.class));
        this.commands.put("Slot_numbers_for_driver_of_age", ParkingLot.class.getMethod("getSlotNumbersFromAge", String.class));
        this.commands.put("Slot_number_for_car_with_number", ParkingLot.class.getMethod("getSlotFromRegno", String.class));
        this.commands.put("Vehicle_registration_number_for_driver_of_age", ParkingLot.class.getMethod("getRegNumbersFromAge", String.class));
        this.commands.put("Leave", ParkingLot.class.getMethod("removeVehicle", String.class));
    }
}
