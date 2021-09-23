package chain_responsibility;

class Test {
    public static void main(String[] args) {
        Principal principal = new Principal(new Teacher(new Student(null)));
        principal.handleRequest(new Request(2));
    }
}

// 责任链
public class ChainResponsibility {
    private final ChainResponsibility next;

    public ChainResponsibility(ChainResponsibility next) {
        this.next = next;
    }

    /**
     * Request handler.
     */
    public void handleRequest(Request req) {
        if (next != null) {
            next.handleRequest(req);
        }
    }
}

// 校长
class Principal extends ChainResponsibility {

    public Principal(ChainResponsibility next) {
        super(next);
    }

    @Override
    public void handleRequest(Request req) {
        if (req.type == 0) {
            System.out.println("校长处理");
        } else {
            super.handleRequest(req);
        }
    }
}

// 老师
class Teacher extends ChainResponsibility {

    public Teacher(ChainResponsibility next) {
        super(next);
    }

    @Override
    public void handleRequest(Request req) {
        if (req.type == 1) {
            System.out.println("老师处理");
        } else {
            super.handleRequest(req);
        }
    }
}

// 学生
class Student extends ChainResponsibility {

    public Student(ChainResponsibility next) {
        super(next);
    }

    @Override
    public void handleRequest(Request req) {
        if (req.type == 2) {
            System.out.println("学生处理");
        } else {
            super.handleRequest(req);
        }
    }
}

class Request {
    int type;

    public Request(int type) {
        this.type = type;
    }
}