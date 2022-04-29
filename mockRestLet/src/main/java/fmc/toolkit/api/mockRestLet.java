package fmc.toolkit.api;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Get;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "mockRest", mixinStandardHelpOptions = true, version = "mockRest 1.3",
        description = "Mock REST Server for testing and troubleshooting")
public class mockRestLet extends ServerResource implements Callable<Integer> {

    @Option(names = {"-p", "--port"}, description = "Listen port")
    private Integer port = 8080;

    @Option(names = {"-m", "--message"}, description = "Response message")
    private String msg = "mockRest is up!";

    @Option(names = {"-v", "--verbose"}, description = "Print details")
    private boolean verbose;

    @Get("txt")
    public String resString() {
        System.out.println(this.msg);
        return this.msg + "\nOpenShift s2i branch javabox (" + System.getenv("HOSTNAME") + ")\n";
    }

    private void printSysProperties() {
        String[] props = new String[]{"java.version", "path.separator", "file.separator", "java.runtime.version", "java.io.tmpdir", "os.name", "user.name", "user.home", "user.dir", "user.timezone", "user.language"};
        System.out.println("-------------------------------");
        for (String key : props) {
            System.out.println(key + ": " + System.getProperty(key, "--"));
        }
        System.out.println("-------------------------------");
    }

    @Override
    public Integer call() throws Exception {
        // Create the HTTP server
        System.out.println(this.port);
        System.out.println(this.msg);
        if (verbose) {
            printSysProperties();
        }
        new Server(Protocol.HTTP, this.port, mockRestLet.class).start();
        return 0;
    }

    public static void main(String[] args) throws Exception {
        new CommandLine(new mockRestLet()).execute(args);
    }

}
