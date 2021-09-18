package template;

class Test {
    public static void main(String[] args) {
        Template template = new MainActivity();
        template.onCreate();
        System.out.println("=================");
        template = new SecondActivity();
        template.onCreate();
    }
}

// BaseActivity
public abstract class Template {

    public void onCreate() {
        if (isSetSystemBarColor()) { // 钩子方法
            setSystemBarColor();
        }
        initView();
        initListener();
        initData();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected boolean isSetSystemBarColor() {
        return true;
    }

    protected void setSystemBarColor() {
        System.out.println("setSystemBarColor");
    }
}

class MainActivity extends Template {

    @Override
    protected void initView() {
        System.out.println("MainActivity=initView");
    }

    @Override
    protected void initListener() {
        System.out.println("MainActivity=initListener");
    }

    @Override
    protected void initData() {
        System.out.println("MainActivity=initData");
    }
}

class SecondActivity extends Template {

    @Override
    protected void initView() {
        System.out.println("SecondActivity=initView");
    }

    @Override
    protected void initListener() {
        System.out.println("SecondActivity=initListener");
    }

    @Override
    protected void initData() {
        System.out.println("SecondActivity=initData");
    }

    @Override
    protected boolean isSetSystemBarColor() {
        return false;
    }
}