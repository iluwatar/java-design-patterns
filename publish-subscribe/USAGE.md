Running the Borrower class:

The class must be run after the TLender class is running since TLender spins up the activeMQ broker.

In order to see the messages being sent to multiple subscribers multiple instance of the TBorrower class need to be run. Either run multiple instances in an IDE or execute the following command in a command line from the root folder after generating the target folder:


mvn exec:java -Dexec.mainClass=com.iluwatar.publishsubscribe.Borrower -Dexec.args="TopicCF RateTopic 6"