package command;

import java.util.ArrayList;

class Test {
    public static void main(String[] args) {
        CommandManager manager = new CommandManager();
        // test execute
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            manager.execute(() -> System.out.println("执行第" + finalI + "次"));
        }
        System.out.println("=============");
        // test undoLastExecute
        for (int i = 0; i < 5; i++) {
            manager.undoLastExecute();
        }
        System.out.println("=============");
        // test redoLastExecute
        for (int i = 0; i < 15; i++) {
            manager.redoLastExecute();
        }
    }
}

public class CommandManager {
    private final ArrayList<Runnable> undoList = new ArrayList<>(); // 取消列表
    private final ArrayList<Runnable> redoList = new ArrayList<>(); // 重做列表

    /**
     * 执行操作
     */
    public void execute(Runnable runnable) {
        runnable.run();
        undoList.add(runnable);
    }

    /**
     * 取消最后的操作
     */
    public void undoLastExecute() {
        lastExecute(undoList, redoList);
    }

    /**
     * 重做最后的执行
     */
    public void redoLastExecute() {
        lastExecute(redoList, undoList);
    }

    private void lastExecute(ArrayList<Runnable> removeLastList, ArrayList<Runnable> addLastList) {
        if (removeLastList.size() > 0) {
            // 从undoList里删除最后一个，然后添加到redoList，最后执行
            Runnable last = removeLastList.get(removeLastList.size() - 1);
            addLastList.add(last);
            removeLastList.remove(last);
            last.run();
        }
    }
}
