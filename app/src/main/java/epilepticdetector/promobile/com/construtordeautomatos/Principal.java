package epilepticdetector.promobile.com.construtordeautomatos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Principal extends ActionBarActivity {

    TicTacToeView mTTTView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mTTTView = (TicTacToeView) this.findViewById(R.id.pntr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();/*
        if ( mTTTView != null ) {
            mTTTView.disableDrawing();
            mTTTView = null;
        }*/
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /*
        if ( mTTTView != null ) {
            mTTTView.disableDrawing();
            mTTTView = null;
        }
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( mTTTView != null ) {
            mTTTView.enableDrawing();
        }
        else {
            mTTTView = (TicTacToeView) this.findViewById(R.id.pntr);
            mTTTView.enableDrawing();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( mTTTView != null ) {
            mTTTView.disableDrawing();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ( mTTTView != null ) {
            mTTTView.enableDrawing();
        }
        else {
            mTTTView = (TicTacToeView) this.findViewById(R.id.pntr);
            mTTTView.enableDrawing();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mTTTView != null ) {
            mTTTView.disableDrawing();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_add_estado:
                mTTTView.addCircle();
                break;
            case R.id.action_resetar:
                mTTTView.removeAllCircles();
                break;
            case R.id.action_add_transiton:
                mTTTView.eventAddTransition = true;
                break;
            case R.id.action_deleta_transiton:
                mTTTView.deleteLine();
                break;
            case R.id.action_altera_estado:
                mTTTView.alteraEstado();
                break;

            case R.id.action_altera_transition:
                mTTTView.alteraTrasicao();
                break;
            case R.id.action_altera_transition_for_state_final:
                mTTTView.changeToEndState();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
