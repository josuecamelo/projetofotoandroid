package br.com.jcamelo.appfotos.model;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ClassFtp {

    FTPClient mFtp;
    String TAG = "classFTP";

    public FTPFile[] Dir(String directory) {
        try {
            FTPFile[] ftpFiles = mFtp.listFiles(directory);
            return ftpFiles;
        } catch (Exception e) {
            Log.e(TAG, "Erro: não foi possível  listar os   arquivos e pastas do diretório " + directory + ". " + e.getMessage());
        }

        return null;
    }

    public boolean changeDirectory(String directory) {
        try {
            mFtp.changeWorkingDirectory(directory);
        } catch (Exception e) {
            Log.e(TAG, "Erro: não foi possível mudar o diretório para " + directory);
        }
        return false;
    }


    public boolean disconnect() {
        try {
            mFtp.disconnect();
            mFtp = null;
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro: ao desconectar. " + e.getMessage());
        }

        return false;
    }

    public boolean connect(String Host, String user, String password, int port) {
        try {
            mFtp = new FTPClient();

            mFtp.connect(Host, port);

            if (FTPReply.isPositiveCompletion(mFtp.getReplyCode())) {
                boolean status = mFtp.login(user, password);

                mFtp.setFileType(FTP.BINARY_FILE_TYPE);
                mFtp.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro: não foi possível conectar" + Host);
        }
        return false;
    }

    public boolean download(String directoryOrigin, String fileOrigin, String fileDestiny) {
        boolean status = false;

        try {
            changeDirectory(directoryOrigin);

            FileOutputStream desFileStream = new FileOutputStream(fileDestiny);
            ;

            mFtp.setFileType(FTP.BINARY_FILE_TYPE);
            mFtp.enterLocalActiveMode();
            mFtp.enterLocalPassiveMode();

            status = mFtp.retrieveFile(fileOrigin, desFileStream);
            desFileStream.close();
            disconnect();

            return status;
        } catch (Exception e) {
            Log.e(TAG, "Erro: Falha ao efetuar download. " + e.getMessage());
        }

        return status;
    }

    public boolean upload(String diretorio, String nomeArquivo) {
        try {
            FileInputStream arqEnviar = new FileInputStream(diretorio);
            mFtp.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
            mFtp.setFileType(FTPClient.STREAM_TRANSFER_MODE);
            mFtp.storeFile(nomeArquivo, arqEnviar);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro: Falha ao efetuar Upload. " + e.getMessage());
            return false;
        }

    }


}
