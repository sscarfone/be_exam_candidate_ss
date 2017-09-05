package com.example.csvtojson.service.impl;

import com.example.csvtojson.service.ConfigProvider;
import com.example.csvtojson.service.CsvFileHandler;
import com.example.csvtojson.service.CsvFileWatcher;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@ApplicationScoped
public class CsvFileWatcherImpl implements CsvFileWatcher {

    @Inject
    private ConfigProvider configProvider;

    @Inject
    private CsvFileHandler csvFileHandler;

    private WatchService watchService;

    public void onStartUp(@Observes @Priority(Interceptor.Priority.PLATFORM_BEFORE + 1)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        System.out.println("CsvFileWatcherImpl Container started with "+obj);

        watchService = FileSystems.getDefault().newWatchService();

        try {
            WatchKey watchKey = configProvider.getSourceDir().register(watchService, ENTRY_CREATE);
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }


    @Override
    public void watchLoop() {

        for (;;) {
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watchService.take();
            } catch (InterruptedException x) {
                System.err.println(x);
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW) {
                    continue;
                }

                // The filename is the
                // context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filename = ev.context();

                csvFileHandler.handleFile(filename);

            }

            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }

    }



}
