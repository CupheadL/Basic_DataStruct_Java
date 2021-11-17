package StackDemo;

public class Calculator {
    public static void main(String[] args){
        //step1:先创建一个表达式
        String expression="300+3*6-2";

        //step2:创建两个栈：数栈&符号栈
        ArrayStack numStack=new ArrayStack(10);
        ArrayStack operStack=new ArrayStack(10);

        //step3:定义需要的相关变量
        int index=0; //作为角标index 用于扫描
        int num1=0;
        int num2=0;
        int oper=0;
        int res=0;
        char ch= ' ';//将每次扫描得到char保存到ch
        String keepNum="";

        //step4:开始while循环的扫描 expression
        while (true){
            ch=expression.substring(index,index+1).charAt(0);
            if (operStack.isOper(ch)){
                if (!operStack.isEmpty()){
                    if (operStack.priority(ch)<=operStack.priority(operStack.peek())){
                        num1=numStack.pop();
                        num2=numStack.pop();
                        oper=operStack.pop();
                        res=numStack.cal(num1,num2,oper);
                        numStack.push(res);
                        operStack.push(ch);
                    }else {
                        operStack.push(ch);
                    }
                }else {
                    operStack.push(ch); //1+3
                }
            }else {
                //如扫描的字符为数字：
                //step1：声明一个全局变量：字符串 (运用字符串去存储长的数字)
                keepNum+=ch;
                //step2:判断1(ch是否为被扫描的字符串中的最后一位)
                if (index==expression.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else {
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))){
                        //判断2：如果下一位为符号：则把keepnum入栈 然后清空keepnum
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum="";
                    }
                }
            }
            //让index +1，并判断是否扫描到expression最后
            index++;
            if (index>=expression.length()){
                break;
            }
        }

        //step5:当表达式扫描完毕,就顺序的从 数栈 和 符号栈 中 pop出相应的数和符号,并运行
        while (true){
            //如果符号栈为空,则计算到最后的结果,数栈中只有一个数字[结果]
            if (operStack.isEmpty()){
                break;
            }
            num1=numStack.pop();
            num2=numStack.pop();
            oper=operStack.pop();
            res=numStack.cal(num1,num2,oper);
            numStack.push(res); //入栈
        }

        //step6:将数栈的最后数,pop出,就是结果
        int res2=numStack.pop();
        System.out.println("表达式:\t"+expression+"="+res2);
    }
}


//先创建一个数组栈+扩展功能

class ArrayStack{
    private int maxSize;
    private int[] Stack;
    private int top = -1;

    public ArrayStack( int maxSize){
        this.maxSize=maxSize;
        Stack=new int[maxSize];
    }

    //布尔函数 & 基本函数
    //栈满
    public boolean isFull(){
        return top==maxSize-1;
    }

    //栈空
    public boolean isEmpty(){
        return top==-1;
    }

    //入栈 push
    public void push(int nums){
        //先判断栈是否为满
        if (isFull()){
            throw new RuntimeException("栈满,无法添入数据");
        }
        top++;
        Stack[top]=nums;
    }

    //出栈 pop
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈空,无法弹出数据");
        }
        int pop_num=Stack[top];
        top--;
        return pop_num;
    }

    //遍历栈
    public void list(){
        if (isEmpty()){
            System.out.println("栈空,无法遍历数据");
            return;
        }
        for (int i = top; i >=0 ; i--) {
            System.out.println("ArrayStack["+i+"]="+Stack[i]);
        }
    }

    //栈模拟计算器的 4个 扩展函数

    //函数1：返回运算符的优先级(用数字表示:数字越大,优先级越高char与int互通)
    public int priority(int oper){
        if (oper=='*'||oper=='/'){
            return 1;
        } else if (oper=='+'||oper=='-'){
            return 0;
        }else {
            return -1;//假定目前的表达式只有+,-.*,/
        }
    }

    //函数2：判断是不是一个运算符
    public boolean isOper(char val){
        return val =='+' || val=='-' || val=='*' || val=='/';
    }

    //函数3：计算方法
    public int cal(int num1,int num2,int oper){
        int res=0;//res 用于存放计算的结果
        switch (oper){
            case '+':
                res=num1+num2;
                break;
            case '-':
                res=num2-num1;
                break;
            case '*':
                res=num1*num2;
                break;
            case '/':
                res=num2/num1;
                break;
            default:
                break;
        }
        return res;
    }

    //函数4：返回栈顶的值
    public int peek() {
        return Stack[top];
    }
}



