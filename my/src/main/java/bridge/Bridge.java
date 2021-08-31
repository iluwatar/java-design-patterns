package bridge;

class Test {
    public static void main(String[] args) {
        new Weapon1(new Enchantment1()).show();
        new Weapon2(new Enchantment2()).show();
    }
}

// 武器
interface Weapon {
    Enchantment getEnchantment();
}

class Weapon1 implements Weapon {
    private final Enchantment enchantment;

    public Weapon1(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }

    public void show() {
        System.out.print("Weapon1 show ");
        enchantment.show();
    }
}

class Weapon2 implements Weapon {
    private final Enchantment enchantment;

    public Weapon2(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }

    public void show() {
        System.out.print("Weapon1 show ");
        enchantment.show();
    }
}


// 魔法
interface Enchantment {
    void show();
}

class Enchantment1 implements Enchantment {
    @Override
    public void show() {
        System.out.println("Enchantment1 add");
    }
}

class Enchantment2 implements Enchantment {
    @Override
    public void show() {
        System.out.println("Enchantment2 add");
    }
}