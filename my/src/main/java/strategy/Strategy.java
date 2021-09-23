package strategy;

import java.util.Arrays;

class Test {
    public static void main(String[] args) {
        int[] ints = {1, 23, 12, 43, 5, 76, 9};
        Sort sort = new Sort(ints);
//        sort.setStrategy(new BubbleSort());
        sort.setStrategy(new SelectionSort());
        sort.sort();
        System.out.println(Arrays.toString(ints));
    }
}

// 冒泡排序、选择排序..
public interface Strategy {
    void sort(int[] array);
}

// 冒泡排序
class BubbleSort implements Strategy {

    @Override
    public void sort(int[] array) {
        int temp; // 临时变量
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

// 选择排序
class SelectionSort implements Strategy {

    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            int min = array[i];
            for (int j = i + 1; j < array.length; j++) {
                if (min > array[j]) { // 说明假定的最小值，并不是最小
                    min = array[j]; // 重置 min
                    minIndex = j; // 重置 minIndex
                }
            }
            if (minIndex != i) {
                array[minIndex] = array[i];
                array[i] = min;
            }
        }
    }
}

class Sort {
    Strategy strategy = new BubbleSort();
    int[] array;

    public Sort(int[] array) {
        this.array = array;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    void sort() {
        strategy.sort(array);
    }
}