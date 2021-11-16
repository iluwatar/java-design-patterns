package domainapp.modules.simple.dom.so;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.isis.persistence.jdo.applib.services.IsisJdoSupport_v3_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import org.apache.isis.applib.services.repository.RepositoryService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleObjects_Test {

    @Mock RepositoryService mockRepositoryService;
    @Mock IsisJdoSupport_v3_2 mockIsisJdoSupport_v3_2;

    SimpleObjects objects;

    @BeforeEach
    public void setUp() {
        objects = new SimpleObjects(mockRepositoryService, mockIsisJdoSupport_v3_2);
    }

    @Nested
    class create {

        @Test
        void happyCase() {

            // given
            final String someName = "Foobar";

            // expect
            when(mockRepositoryService.persist(
                    argThat((ArgumentMatcher<SimpleObject>) simpleObject -> Objects.equals(simpleObject.getName(), someName)))
            ).then((Answer<SimpleObject>) invocation -> invocation.getArgument(0));

            // when
            final SimpleObject obj = objects.create(someName);

            // then
            assertThat(obj).isNotNull();
            assertThat(obj.getName()).isEqualTo(someName);
        }
    }

    @Nested
    class ListAll {

        @Test
        void happyCase() {

            // given
            final List<SimpleObject> all = new ArrayList<>();

            // expecting
            when(mockRepositoryService.allInstances(SimpleObject.class))
                .thenReturn(all);

            // when
            final List<SimpleObject> list = objects.listAll();

            // then
            assertThat(list).isEqualTo(all);
        }
    }
}
