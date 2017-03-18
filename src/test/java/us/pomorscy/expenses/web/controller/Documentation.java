package us.pomorscy.expenses.web.controller;

import org.junit.Rule;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public abstract class Documentation {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    protected RestDocumentationResultHandler documentationHandler = document("{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()));

}
