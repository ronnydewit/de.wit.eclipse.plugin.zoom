package de.wit.eclipse.plugin.zoom.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.wit.eclipse.plugin.zoom.util.Zoom;

public class OutHandler extends AbstractHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        Zoom.out();

        return null;
    }

}
