<?xml version="1.0"?><Interface xmlns="http://www.corelogicllc.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" Document="Shipment" Production="true" SchemaVersion="2" xsi:schemaLocation="http://www.corelogicllc.com http://corelogicllc.com/docs/b2b/v2/Shipment.xsd">
  <Header>
    <TenantID>103</TenantID>
    <SenderCode>410</SenderCode>
    <ReceiverCode>103</ReceiverCode>
    <DocumentCreation>${documentCreation}</DocumentCreation>
    <MessageReference>${messageNumber}</MessageReference>
    <Reference key="Source">${source}</Reference>
  </Header>
  <Shipment Identification="${asusSo}">
    <Details>
      <CustomerCode>410</CustomerCode>
      <VendorCode>728</VendorCode>
      <WarehouseCode>6664</WarehouseCode>
      <PrimaryContact>
        <Name>Godwin Yan</Name>
        <Phone>510-739-3777-64519</Phone>
      </PrimaryContact>
      <RequestedShipDate>${dueDate}</RequestedShipDate>
      <Taxable xsi:nil="true"/>
      <Subtotal>0</Subtotal>
      <Notes>${formaPalletType}</Notes>
      <Notes>Shipment Method: TRUCK</Notes>
      <Notes/>
      <Reference key="BGM+220:04">${asusSo}</Reference>
      <Reference key="REF+ACD:02">${combinedId}</Reference>
    </Details>
    <Shipper xsi:nil="true"/>
    <BillingAddress AddressLine="800 Corporate Way, Frement, USA, 94539" AddressLineCountry="USA" BuyerCode="" Dun14="" GLN="" SupplierCode="ACI-AIRSRL-AR-C-ICON" xsi:nil="true"/>
    <ShippingAddress AddressLine="${shippingAddress}" AddressLineCountry="USA" BuyerCode="" Dun14="" GLN="" SupplierCode="${addressIdentifier}" xsi:nil="true"/>
    <PayToAddress xsi:nil="true"/>
    <EndUserInformation xsi:nil="true"/>
    <UltimateConsignee xsi:nil="true"/>
    <OutboundCarrier xsi:nil="true"/>
    <LineItems>
    <#list lineItems as lineItem>
      <Line LIN="${lineItem.itemLineNumber}.0">
        <PartNumber Intangible="false">${lineItem.asusPartNumber}</PartNumber>
        <PartDescription xsi:nil="true"/>
        <CustomPartDescription xsi:nil="true"/>
        <OrderedQty>${lineItem.quantity}</OrderedQty>
        <ListPrice>${lineItem.unitPrice}</ListPrice>
        <Discount xsi:nil="true"/>
        <ContractDiscount xsi:nil="true"/>
        <UnitPrice xsi:nil="true"/>
        <ExtendedPrice xsi:nil="true"/>
        <ConditionCode>CC</ConditionCode>
      </Line>
      </#list>
    </LineItems>
  </Shipment>
</Interface>