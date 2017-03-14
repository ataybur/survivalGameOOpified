package com.ataybur.pojo;

import com.ataybur.pojo.base.Character;

public class Hero extends Character {

    public Hero() {
	super();
    }

    public Hero(Hero instance) {
	super(instance);
    }

    @Override
    public String toString() {
	return "Hero [getHp()=" + getHp() + ", getAttackPoint()=" + getAttackPoint() + "]";
    }

}
