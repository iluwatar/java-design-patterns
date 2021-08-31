package singleton;

import java.lang.reflect.Constructor;

class Test {
    public static void main(String[] args) {
        System.out.println("SingletonStaticField=" + (SingletonStaticField.getInstance() == SingletonStaticField.getInstance()));
//        System.out.println("SingletonStaticFieldReflect=" + (SingletonStaticField.getInstance() == newInstance(SingletonStaticField.class)));

        System.out.println("SingletonStaticCodeBlock=" + (SingletonStaticCodeBlock.getInstance() == SingletonStaticCodeBlock.getInstance()));

        System.out.println("SingletonLazyUnSynchronized=" + (SingletonLazyUnSynchronized.getInstance() == SingletonLazyUnSynchronized.getInstance()));

        System.out.println("SingletonLazySynchronizedFun=" + (SingletonLazySynchronizedFun.getInstance() == SingletonLazySynchronizedFun.getInstance()));

        System.out.println("SingletonLazySynchronizedCodeBlock=" + (SingletonLazySynchronizedCodeBlock.getInstance() == SingletonLazySynchronizedCodeBlock.getInstance()));

        System.out.println("SingletonLazyDoubleCheck=" + (SingletonLazyDoubleCheck.getInstance() == SingletonLazyDoubleCheck.getInstance()));

        System.out.println("SingletonInnerClass=" + (SingletonInnerClass.getInstance() == SingletonInnerClass.getInstance()));

        System.out.println("SingletonEnum=" + (SingletonEnum.INSTANCE == SingletonEnum.INSTANCE));
    }

    private static Object newInstance(Class clazz) {
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = constructors[0];
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// 1.饿汉式，静态变量
class SingletonStaticField {
    private static final SingletonStaticField INSTANCE = new SingletonStaticField();

    private SingletonStaticField() {
        // 防止反射调用
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized.");
        }
    }

    public static SingletonStaticField getInstance() {
        return INSTANCE;
    }
}

// 2.饿汉式，静态代码块
class SingletonStaticCodeBlock {
    private static final SingletonStaticCodeBlock INSTANCE;

    private SingletonStaticCodeBlock() {
        // 防止反射调用
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized.");
        }
    }

    static {
        INSTANCE = new SingletonStaticCodeBlock();
    }

    public static SingletonStaticCodeBlock getInstance() {
        return INSTANCE;
    }
}

// 3.懒汉式，未同步
class SingletonLazyUnSynchronized {
    private static SingletonLazyUnSynchronized INSTANCE;

    private SingletonLazyUnSynchronized() {
    }

    public static SingletonLazyUnSynchronized getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonLazyUnSynchronized();
        }
        return INSTANCE;
    }
}

// 4.懒汉式，同步方法
class SingletonLazySynchronizedFun {
    private static volatile SingletonLazySynchronizedFun INSTANCE;

    private SingletonLazySynchronizedFun() {
    }

    public static synchronized SingletonLazySynchronizedFun getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonLazySynchronizedFun();
        }
        return INSTANCE;
    }
}

// 5.懒汉式，同步代码块
class SingletonLazySynchronizedCodeBlock {
    private static volatile SingletonLazySynchronizedCodeBlock INSTANCE;

    private SingletonLazySynchronizedCodeBlock() {
    }

    public static SingletonLazySynchronizedCodeBlock getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonLazySynchronizedCodeBlock.class) {
                INSTANCE = new SingletonLazySynchronizedCodeBlock();
            }
        }
        return INSTANCE;
    }
}

// 6.懒汉式，双if
class SingletonLazyDoubleCheck {
    private static volatile SingletonLazyDoubleCheck INSTANCE;

    private SingletonLazyDoubleCheck() {
    }

    public static SingletonLazyDoubleCheck getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonLazyDoubleCheck.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonLazyDoubleCheck();
                }
            }
        }
        return INSTANCE;
    }
}

// 7.静态内部类
class SingletonInnerClass {

    private SingletonInnerClass() {
    }

    public static SingletonInnerClass getInstance() {
        return InnerHolder.INSTANCE;
    }

    static class InnerHolder {
        private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
    }
}

// 8.枚举
enum SingletonEnum {
    INSTANCE
}