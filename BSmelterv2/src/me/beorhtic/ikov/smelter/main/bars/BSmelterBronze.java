package me.beorhtic.ikov.smelter.main.bars;

import me.beorhtic.ikov.smelter.main.BSmelterMain;
import me.beorhtic.ikov.smelter.main.SmeltConstants;
import simple.api.ClientContext;

public abstract class BSmelterBronze extends BSmelterMain {

    private ClientContext ctx;

    @Override
    public void onExecute() {
        System.out.println("Starting...");

    }

    @Override
    public void onProcess() {

        if (ctx.inventory.populate().filter(436, 438).isEmpty()) {
            ctx.pathing.walkPath(SmeltConstants.PATH_TO_BANK_AREA, true);
            ctx.sleep(1000);
                System.out.println("Need ore...");
                ctx.bank.openBank();
                ctx.bank.deposit(2349, 14);
                ctx.bank.withdraw(438, 14);
                ctx.bank.withdraw(436, 14);
                ctx.sleep(1000, 2000);
                ctx.bank.closeBank();
                ctx.onCondition(() -> !ctx.bank.openBank(), 250, 100);

            } else {
                // We are in the area... so lets grab the furnace
                if (ctx.players.getLocal().getAnimation() == -1) {
                    if (!ctx.objects.populate().filter(24009).isEmpty()) {
                        ctx.objects.next().interact(900);
                        ctx.sleep(2500);
                        ctx.menuActions.sendAction(315, 0, 0, 51400);
                        ctx.sleep(2500);
                    } else {
                        System.out.println("We can't find the furnace...!");
                    }
                } else {
                    ctx.sleep(500);
                    if (ctx.players.getLocal().getAnimation() == -1) {
                        System.out.println("We need to interact again");
                    }
                }
            }
    }

    @Override
    public void onTerminate() {

    }
}
