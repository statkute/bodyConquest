package com.cauldron.bodyconquest.AIs;

class Node {

    int x;
    int y;
    double hValue;
    int gValue;
    double fValue;
    Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
    }
}
