package domainapp.webapp.unittests.mavendeps.lockdown;

import domainapp.webapp.util.CurrentVsApprovedApprovalTextWriter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.writers.ApprovalTextWriter;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import static org.approvaltests.Approvals.getReporter;
import static org.approvaltests.Approvals.verify;

public class LockDownMavenDeps_Test {

    @BeforeEach
    public void setUp() throws Exception {
        Assumptions.assumeThat(System.getProperty("mavendeps.lockdown")).isNotNull();
    }

    @UseReporter(DiffReporter.class)
    @Test
    public void compare_list() throws Exception {
        final String variant = "list";
        final String current = sort(read(variant));
        verify(approvalTextWriter(current, "txt"), namerFor(variant), getReporter());
    }

    @UseReporter(DiffReporter.class)
    @Test
    public void compare_tree() throws Exception {
        final String variant = "tree";
        final String current = read(variant);
        verify(approvalTextWriter(current, "txt"), namerFor(variant), getReporter());
    }

    private static ApprovalTextWriter approvalTextWriter(final String received, final String fileExtensionWithoutDot) {
        return new CurrentVsApprovedApprovalTextWriter(received, fileExtensionWithoutDot);
    }

    private StackTraceNamer namerFor(String variant) {
        return new StackTraceNamer() {
            @Override
            public String getApprovalName() {
                return "LockDownMavenDeps_Test." + variant;
            }
        };
    }

    private String read(final String goal) throws IOException {
        final URL resource = Resources.getResource(getClass(),
                String.format("current/%s.%s.txt", getClass().getSimpleName(), goal));
        return Resources.toString(resource, Charsets.UTF_8);
    }

    private static String sort(final String unsorted) {
        final String[] lines = unsorted.split("[\r\n]+");
        Arrays.sort(lines);
        return String.join("\n", lines);
    }

}
