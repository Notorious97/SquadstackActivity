package com.squadstack;

class Main {
    Main() {
    }
    // Runner function
    public static void main(String[] args) {
        FileParser parser = new FileParser();
        parser.processFile("input.txt");
    }
}