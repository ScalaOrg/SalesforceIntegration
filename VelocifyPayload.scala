package com.clara.velocify

import java.net.URLDecoder
import java.nio.charset.StandardCharsets.UTF_8

import com.expeditelabs.thrift.scala.{LoanIntent, PortalLead, User}
import com.expeditelabs.util.request.RequestUtils

object VelocifyPayload {
  val DefaultFieldValues =
    Map("Company" -> Some("Expedite"))

  def fromLead(
    user: User,
    loanDatum: LoanIntent,
    lead: PortalLead
  ): VelocifyPayload = {
    // N.B. the `landingQueryString` is url encoded so we have to decode before parsing stuff out
    val queryParams = lead.landingQueryString match {
      case Some(queryString) =>
        val urlDecodedString = URLDecoder.decode(queryString, UTF_8.name())
        RequestUtils.parseQueryString(urlDecodedString)

      case None =>
        Map.empty[String, String]
    }

    VelocifyPayload(
      primaryBorrowerEmail = Some(user.email),
      primaryBorrowerPhone = lead.leadPhoneNumber,
      borrowerFirstName    = Some(user.first),
      borrowerLastName     = Some(user.last),
      fullName             = Some(s"${user.first} ${user.last}"),
      loanId               = loanDatum.loanId.map(_.toString),
      yourGoal             = loanDatum.goal.map(_.name),
      loanAmount           = loanDatum.estimatedPropertyValue.map(_.value.toString),
      propertyType         = (loanDatum.typeOfHome.orElse(loanDatum.homeType)).map(_.name),
      state                = loanDatum.usState.map(_.name),
      propertyState        = loanDatum.usState.map(_.name),
      leadSource           = queryParams.get("utm_source"),
      sourceDetail         = queryParams.get("utm_medium"),
      channel              = queryParams.get("utm_campaign")
    )
  }
}

case class VelocifyPayload(
  fullName:                      Option[String] = None,
  leadSource:                    Option[String] = None,
  yourGoal:                      Option[String] = None,
  propertyType:                  Option[String] = None,
  propertyCity:                  Option[String] = None,
  propertyState:                 Option[String] = None,
  longTermLiabilities:           Option[String] = None,
  streetAddress:                 Option[String] = None,
  aptOrSuite:                    Option[String] = None,
  city:                          Option[String] = None,
  state:                         Option[String] = None,
  zipCode:                       Option[String] = None,
  dateOfBirth:                   Option[String] = None,
  primaryBorrowerEmail:          Option[String] = None,
  primaryBorrowerPhone:          Option[String] = None,
  primaryBorrowerSSN:            Option[String] = None,
  borrowerFirstName:             Option[String] = None,
  borrowerLastName:              Option[String] = None,
  maritalStatus:                 Option[String] = None,
  creditProfile:                 Option[String] = None,
  assessedCreditScore:           Option[String] = None,
  occupancyStatus:               Option[String] = None,
  bankruptPast7Years:            Option[String] = None,
  ownedPropLast3Years:           Option[String] = None,
  employerName:                  Option[String] = None,
  endDate:                       Option[String] = None,
  positionTitle:                 Option[String] = None,
  startDate:                     Option[String] = None,
  positionTitleCoBorrower:       Option[String] = None,
  totalMonthlyIncome:            Option[String] = None,
  typeOfIncome:                  Option[String] = None,
  coBorrowerEmail:               Option[String] = None,
  assessedCreditScoreCoBorrower: Option[String] = None,
  employerNameCoBorrower:        Option[String] = None,
  endDateCoBorrower:             Option[String] = None,
  startDateCoBorrower:           Option[String] = None,
  totalMonthlyIncomeCoBorrower:  Option[String] = None,
  otherIncomeCoBorrower:         Option[String] = None,
  typeOtherLiabilities:          Option[String] = None,
  loanAmount:                    Option[String] = None,
  existingHomeValue:             Option[String] = None,
  channel:                       Option[String] = None,
  sourceDetail:                  Option[String] = None,
  ltv:                           Option[String] = None,
  qfNumber:                      Option[String] = None,
  loanId:                        Option[String] = None
) {
  import VelocifyPayload._

  def toMap: Map[String, Option[String]] = {
    Map(
      "FullName"                      -> fullName,
      "LeadSource"                    -> leadSource,
      "YourGoal"                      -> yourGoal,
      "PropertyType"                  -> propertyType,
      "City"                          -> propertyCity,
      "State"                         -> propertyState,
      "LongTermLiabilities"           -> longTermLiabilities,
      "DateOfBirth"                   -> dateOfBirth,
      "PrimaryBorrowerEmail"          -> primaryBorrowerEmail,
      "BorrowerFirstName"             -> borrowerFirstName,
      "PrimaryBorrowerPhone"          -> primaryBorrowerPhone,
      "BorrowerLastName"              -> borrowerLastName,
      "MaritalStatus"                 -> maritalStatus,
      "PrimaryBorrowerSSN"            -> primaryBorrowerSSN,
      "MailingState"                  -> state,
      "MailingAddress"                -> streetAddress,
      "MailingCity"                   -> city,
      "MailingAddress2"               -> aptOrSuite,
      "MailingZip"                    -> zipCode,
      "CreditProfile"                 -> creditProfile,
      "AssessedCreditScore"           -> assessedCreditScore,
      "OccupancyStatus"               -> occupancyStatus,
      "BankruptPast7Years"            -> bankruptPast7Years,
      "OwnedPropLast3Years"           -> ownedPropLast3Years,
      "EmployerName"                  -> employerName,
      "EndDate"                       -> endDate,
      "PositionTitle"                 -> positionTitle,
      "StartDate"                     -> startDate,
      "PositionTitleCoBorrower"       -> positionTitleCoBorrower,
      "TotalMonthlyIncome"            -> totalMonthlyIncome,
      "TypeOfIncome"                  -> typeOfIncome,
      "CoBorrowerEmail"               -> coBorrowerEmail,
      "AssessedCreditScoreCoBorrower" -> assessedCreditScoreCoBorrower,
      "EmployerNameCoBorrower"        -> employerNameCoBorrower,
      "EndDateCoBorrower"             -> endDateCoBorrower,
      "StartDateCoBorrower"           -> startDateCoBorrower,
      "TotalMonthlyIncomeCoBorrower"  -> totalMonthlyIncomeCoBorrower,
      "OtherIncomeCoBorrower"         -> otherIncomeCoBorrower,
      "TypeOtherLiabilities"          -> typeOtherLiabilities,
      "LoanAmount"                    -> loanAmount,
      "ExistingHomeValue"             -> existingHomeValue,
      "Channel"                       -> channel,
      "SourceDetail"                  -> sourceDetail,
      "QFNumber"                      -> qfNumber,
      "LoanToValueLTV"                -> ltv,
      "LoanIDExpedite"                -> loanId
    ) ++ DefaultFieldValues
  }
}
