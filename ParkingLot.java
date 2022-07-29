package com.squadstack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ParkingLot {
    int Capacity = 0;
    ArrayList<Integer> availableSlots;
    HashMap<String, ArrayList<String>> ageRegnoMap;
    HashMap<String, String> regnoSlotMap;
    HashMap<String, ParkingLot.Vehicle> slotVehicleMap;

    public ParkingLot() {

    }

    public void initParkingLot(String Capacity) {
        try {
            this.Capacity = Integer.parseInt(Capacity);
        } catch (Exception var3) {
            System.out.println("Cannot convert String to int, invalid input");
            var3.printStackTrace();
        }

        this.availableSlots = new ArrayList();

        for(int i = 1; i <= this.Capacity; ++i) {
            this.availableSlots.add(i);
        }

        this.ageRegnoMap = new HashMap();
        this.regnoSlotMap = new HashMap();
        this.slotVehicleMap = new HashMap();
        System.out.println("Created parking of " + this.Capacity + " slots");
    }

    public void parkVehicle(String regNo, String age) {
        if (this.Capacity == 0) {
            System.out.println("Error: Parking Lot is not initialised");
        } else if (this.slotVehicleMap.size() == this.Capacity) {
            System.out.println("Error: Parking Lot is full");
        } else {
            Collections.sort(this.availableSlots);
            String slot = ((Integer)this.availableSlots.get(0)).toString();
            ParkingLot.Vehicle vehicle = new ParkingLot.Vehicle(regNo, age);
            this.regnoSlotMap.put(regNo, slot);
            this.slotVehicleMap.put(slot, vehicle);
            if (this.ageRegnoMap.containsKey(age)) {
                ArrayList<String> regnoList = (ArrayList)this.ageRegnoMap.get(age);
                regnoList.add(regNo);
                this.ageRegnoMap.remove(age);
                this.ageRegnoMap.put(age, regnoList);
            } else {
                ArrayList<String> regnoList = new ArrayList();
                regnoList.add(regNo);
                this.ageRegnoMap.put(age, regnoList);
            }

            this.availableSlots.remove(0);
            System.out.println("Car with vehicle registration number \"" + regNo + "\" has been parked at slot number " + slot);
        }

    }

    public void removeVehicle(String slotNo) {
        if (this.Capacity == 0) {
            System.out.println("Error: Parking lot is not initialised");
        } else if (this.slotVehicleMap.size() > 0) {
            ParkingLot.Vehicle vehicle = (ParkingLot.Vehicle)this.slotVehicleMap.get(slotNo);
            if (vehicle != null) {
                this.slotVehicleMap.remove(slotNo);
                this.regnoSlotMap.remove(vehicle.regNo);
                ArrayList<String> regnolist = (ArrayList)this.ageRegnoMap.get(vehicle.driverAge);
                if (regnolist.contains(vehicle.regNo)) {
                    regnolist.remove(vehicle.regNo);
                }

                this.availableSlots.add(Integer.parseInt(slotNo));
                System.out.println(String.format("Slot number %s vacated, the car with vehicle registration number \"%s\" left the space, the driver of the car was of age %s", slotNo, vehicle.regNo, vehicle.driverAge));
            }
        } else {
            System.out.println("Error: Parking lot is empty");
        }

    }

    public void getSlotNumbersFromAge(String age) {
        if (this.Capacity == 0) {
            System.out.println("Error: Parking lot is not initialised");
        } else if (this.ageRegnoMap.containsKey(age)) {
            ArrayList<String> regnolist = (ArrayList)this.ageRegnoMap.get(age);
            ArrayList<Integer> slotlist = new ArrayList<Integer>();

            int i;
            for(i = 0; i < regnolist.size(); ++i) {
                slotlist.add(Integer.valueOf((String)this.regnoSlotMap.get(regnolist.get(i))));
            }

            Collections.sort(slotlist);

            for(i = 0; i < slotlist.size(); ++i) {
                if (i != slotlist.size() - 1) {
                    System.out.print(slotlist.get(i) + ",");
                } else {
                    System.out.print(slotlist.get(i));
                }
            }

            System.out.println();
        }

    }

    public void getRegNumbersFromAge(String age) {
        if (this.Capacity == 0) {
            System.out.println("Error: Parking lot is not initialised");
        } else if (this.ageRegnoMap.containsKey(age)) {
            ArrayList<String> regnolist = (ArrayList)this.ageRegnoMap.get(age);

            for(int i = 0; i < regnolist.size(); ++i) {
                if (i != regnolist.size() - 1) {
                    PrintStream var10000 = System.out;
                    Object var10001 = regnolist.get(i);
                    var10000.println((String)var10001 + ",");
                } else {
                    System.out.println((String)regnolist.get(i));
                }
            }
        }

    }

    public void getSlotFromRegno(String regno) {
        if (this.Capacity == 0) {
            System.out.println("Parking lot is not initialised");
        } else if (this.regnoSlotMap.containsKey(regno)) {
            System.out.println((String)this.regnoSlotMap.get(regno));
        }

    }

    private class Vehicle {
        public String regNo;
        public String driverAge;

        public Vehicle(String regNo, String driverAge) {
            this.regNo = regNo;
            this.driverAge = driverAge;
        }
    }
}
