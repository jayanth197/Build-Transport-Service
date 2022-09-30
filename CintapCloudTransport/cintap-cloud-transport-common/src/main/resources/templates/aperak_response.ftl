<?xml version="1.0" encoding="ISO-8859-1"?>
<Interface xmlns="http://www.corelogicllc.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" Document="GenericAcknowledgement" Production="true" SchemaVersion="2" xsi:schemaLocation="http://www.corelogicllc.com https://b2b.corelogicllc.com/schemas/v2/GenericAcknowledgement.xsd">
  <Header>
    <TenantID>103</TenantID>
    <SenderCode>103</SenderCode>
    <ReceiverCode>410</ReceiverCode>
    <DocumentCreation>${documentCreation}</DocumentCreation>
    <MessageReference>${messageReference}</MessageReference>
    <Reference key="Source">${source}</Reference>
  </Header>
  <Acknowledgment DocumentAck="${documentAck}" Identification="${identification}" ReferenceNumberAck="${referenceNumberAck}">
    <Details>
      <Reference key="REF+ACD:02">T-${identification}</Reference>
      <Reference key="BGM+220:04">${identification}</Reference>
    </Details>
    <ResponseCode>${responseCode}</ResponseCode>
  </Acknowledgment>
</Interface>
