package classes;

public class runnableThread implements Runnable{

    private final taskManager manager;
    private final Collector restultCollector;

    public runnableThread(taskManager tskMng, Collector resCol){
        this.manager = tskMng;
        this.restultCollector = resCol;
    }

    public int checkPrime(int n) {
        boolean isPrime = true;
        try {
            Thread.sleep(5000);
            for (int i = 2; i <= n / 2; i++) {
                if (n % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        } catch (InterruptedException e) {
            return -1;
        }

        if (isPrime && n > 1) {
            return n;
        }
        return -1;
    }


    @Override
    public void run(){

        while (!this.manager.flag) {
            int num;
            try {
                num = this.manager.takeTask();
            } catch (InterruptedException e) {
                return;
            }

            int result = checkPrime(num);

            if (result > 0) {
                this.restultCollector.addResult(result);
            }
        }

        //finishing what has been started
        while (!manager.isEmpty()) {
            int num;
            try {
                num = this.manager.takeTask();
            } catch (InterruptedException e) {
                return;
            }

            int result = checkPrime(num);

            if (result > 0) {
                this.restultCollector.addResult(result);
            }
        }
    }
}
