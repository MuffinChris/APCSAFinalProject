public class PlayerInfoUpdater implements Runnable {

    private Player player;

    public PlayerInfoUpdater(Player p) {
        player = p;
    }

    public void run() {
        while (true) {
            player.broadcastMessage("SERVERINFO: " + player.getName() + "," + player.getX() + "," + player.getY() + "," + player.getOne() + "," + player.getTwo() + "," + player.getThree());
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
