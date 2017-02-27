package nl.utils.dewaal.pickatag;

import java.util.TimerTask;

import nl.utils.dewaal.pickatag.ui.frmPickATagController;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetTagLineTask extends TimerTask {
    
    private frmPickATagController _myController = null;
    private static final Logger log = LogManager.getLogger(GetTagLineTask.class);
    private static int _runs = 0;
    
    public GetTagLineTask() {
    }

    public void run() {
        _runs++;
        log.info("Run enabled, run no.: " + _runs);
        //simulate click here...
        _myController.iets();
    }

    public void setMyController(frmPickATagController myController) {
        this._myController = myController;
    }
}