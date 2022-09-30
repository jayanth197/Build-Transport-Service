<?xml version="1.0"?><Interface xmlns="http://www.corelogicllc.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" Document="PurchaseOrder" Production="true" SchemaVersion="2" xsi:schemaLocation="http://www.corelogicllc.com http://corelogicllc.com/docs/b2b/v2/PurchaseOrder.xsd">
  <Header>
    <TenantID>103</TenantID>
    <SenderCode>410</SenderCode>
    <ReceiverCode>103</ReceiverCode>
    <DocumentCreation>${documentCreation}</DocumentCreation>
    <MessageReference>${messageNumber}</MessageReference>
    <Reference key="Source">${source}</Reference>
  </Header>
  <PurchaseOrder Identification="${bgmDocMsgNumber}">
    <Details>
      <CustomerCode>410</CustomerCode>
      <VendorCode>2323</VendorCode>
      <WarehouseCode>1831</WarehouseCode>
      <PrimaryContact xsi:nil="true"/>
      <RequestedShipDate xsi:nil="true"/>
      <Taxable xsi:nil="true"/>
      <Subtotal CurrencyCode="${currency}">${invoiceAmount}</Subtotal>
      <Notes/>
    </Details>
    <Shipper SupplierCode="${addreessIdentifier}" xsi:nil="true"/>
    <BillingAddress>
      <Company>ASUS Computer International</Company>
      <AddressLine>800 Corporate Way</AddressLine>
      <City>Fremont</City>
      <State>CA</State>
      <PostalCode>94539</PostalCode>
      <Country>USA</Country>
    </BillingAddress>
    <ShippingAddress>
      <Company>ASUS Computer International C/O Freight Logistics</Company>
      <AddressLine>3505 NW 107th Ave</AddressLine>
      <AddressLine>Suite C</AddressLine>
      <City>Doral</City>
      <State>FL</State>
      <PostalCode>33178</PostalCode>
      <Country>USA</Country>
    </ShippingAddress>
    <EndUserInformation xsi:nil="true"/>
    <LineItems>
    <#list lineItems as lineItem>
      <Line LIN="${lineItem.itemLineNumber}.0" SalesOrder="0" VendorOrderID="${lineItem.referenceIdentifier}">
        <PartNumber Intangible="false" VendorModelNumber="${lineItem.vendorPartDescription}">${lineItem.vendorPartNumber}</PartNumber>
        <PartDescription/>
        <CustomPartDescription/>
        <OrderedQty>${lineItem.quantity}</OrderedQty>
        <ListPrice>${lineItem.unitPrice}</ListPrice>
        <Discount/>
        <ContractDiscount/>
        <UnitPrice/>
        <ExtendedPrice/>
      </Line>
      </#list>
    </LineItems>
  </PurchaseOrder>
</Interface>