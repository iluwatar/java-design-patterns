public ISOMsg isoMsgChequeStop(StopChequePaymentParams stopChequePaymentParams) {
  ISOMsg m = new ISOMsg();

  String accountNumber = stopChequePaymentParams.getAccountNumber();
  String reasonCode = stopChequePaymentParams.getReasonCode();
  Integer chequeStartNo = stopChequePaymentParams.getChequeStartNo();
  Integer noOfLeaves = stopChequePaymentParams.getLeavesCount();

  try {

    m.setMTI(MSGComposerConstants.MTI_TX); //MTI
    //            m.set(2, "0000000000000000"); //Primary Account Number/Customer ID
    m.set(2, stopChequePaymentParams.getInitiator()); //Primary Account Number/Customer ID/ Card number (length=16)
    m.set(3, MSGComposerConstants.PROCESSING_CODE_CHQ_STOP); //Processing Code
    m.set(4, "0000000000000000");
    String stan = Util.padRight(stanGenerator.generateSTAN(), " ", 12);
    m.set(11, stan); //System Trace Audit Number
    m.set(12, Util.getCurrentDateTime()); //Local Transaction Date And Time
    m.set(17, Util.getCurrentDate()); //Capture Date
    m.set(32, MSGComposerConstants.ACQUIRING_II_CODE); //Acquiring Institution Identification Code
    m.set(37, "412812134941"); //constant
    m.set(41, "00390039"); //atm id
    m.set(42, Util.padRight("1000008", " ", 15)); //atm id
    m.set(43, "LAXMI ATM");

    if (stopChequePaymentParams.hasPartners()) {
      m.set(46, stopChequePaymentParams.getPartnerPattern());
      //                m.set(46, "99524D000000000000000000000000D0000000000000000524");
    }

    m.set(49, stopChequePaymentParams.getTransactionCurrencyCode());

    String field62 = Util.padLeft(chequeStartNo.toString(), " ", 16) + Util.padLeft(noOfLeaves.toString(), "0", 4) + Util.padRight(reasonCode, " ", 5);

    m.set(62, field62);
    m.set(102, accountNumber);
    m.set(123, MSGComposerConstants.DELIVERY_CH_CTRL_ID); //Delivery Channel Controller ID

  } catch(ISOException e) {
    e.printStackTrace();
  }
  return m;
}
