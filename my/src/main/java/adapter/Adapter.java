package adapter;

class Test {
    public static void main(String[] args) {
        // 之前使用
        RowingBoat rowingBoat = new RowingBoatImp();
        Captain captain = new Captain(rowingBoat);
        captain.row();

        // 现在使用
        RowingBoat rowingBoat1 = new FishingBoatAdapter(new FishingBoat());
        Captain captain1 = new Captain(rowingBoat1);
        captain1.row();
    }
}

// 船长
class Captain {
    private RowingBoat rowingBoat;

    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public void row() {
        rowingBoat.row();
    }
}

interface RowingBoat {
    void row();
}

class FishingBoatAdapter implements RowingBoat {
    private FishingBoat fishingBoat;

    public FishingBoatAdapter(FishingBoat fishingBoat) {
        this.fishingBoat = fishingBoat;
    }

    @Override
    public void row() {
        fishingBoat.sail();
    }
}

// 划船
@Deprecated
class RowingBoatImp implements RowingBoat {

    public void row() {
        System.out.println("划船在滑行");
    }
}

// 渔船
class FishingBoat {
    void sail() {
        System.out.println("渔船在航行");
    }
}