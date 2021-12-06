package domainapp.modules.simple.dom.so;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

@ExtendWith(MockitoExtension.class)
class SimpleObject_Test {

    @Mock TitleService mockTitleService;
    @Mock MessageService mockMessageService;
    @Mock RepositoryService mockRepositoryService;

    SimpleObject object;

    /**
     * Initialize a SimpleObject with name "Foo", and set up titleService, messageService, and repositoryService 
     */
    @BeforeEach
    public void setUp() throws Exception {
        object = SimpleObject.withName("Foo");
        object.titleService = mockTitleService;
        object.messageService = mockMessageService;
        object.repositoryService = mockRepositoryService;
    }
    /**
     * Test that name is properly set 
     */
    @Nested
    public class withName {
        
        @Test
        void happy_case() {
            // expecting
            assertThat(object.getName()).isEqualTo("Foo");
        }
    }
    /**
     * Test that object title field is set properly as ("Object: [name]") 
     */
    @Nested
    public class title {
        
        @Test
        void happy_case() {
            // expecting
            assertThat(object.title()).isEqualTo("Object: Foo");
        }
    }
    /**
     * Test that SimpleObject returns the correct name when name field is updated 
     */
    @Nested
    public class updateName {

        @Test
        void happy_case() {
            // given
            assertThat(object.getName()).isEqualTo("Foo");

            // when
            object.updateName("Bar");

            // then
            assertThat(object.getName()).isEqualTo("Bar");
        }

    }
    /**
     * Test that notification of user is successful when object is deleted 
     */
    @Nested
    class delete {

        @Test
        void happy_case() throws Exception {

            // given
            assertThat(object).isNotNull();

            // expecting
            when(mockTitleService.titleOf(object)).thenReturn("Foo");

            // when
            object.delete();

            // then
            verify(mockMessageService).informUser("'Foo' deleted");
            verify(mockRepositoryService).removeAndFlush(object);
        }
    }
}