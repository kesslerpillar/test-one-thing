# Overview
Our team had some confusion over what it meant to only test one thing in each unit test method.  
I put this eample together to flush out each approach in order to understand their pros and cons.

# Approaches
1. Limit each test method to only one assertion. This is explored in the `SeparateBillerTest`
1. Test one path through a method-under-test.  This will result in 1 or more assertions per test method. 
This is explored in the `UnifiedBillerTest`
1. Test all paths in a single test method.  This is the anti-pattern that the only-test-one thing in a method rule
was trying to solve.  This is explored in the `EverythingBillerTest`

_Note: All three tests provide 100% test coverage for Biller.sendInvoicesToCustomersWithOutstandingBalances() even though
their quality of coverage is not equal._

# 1. Only One Assertion
## Pros
1. Test methods may be shorter and more focused.

## Cons
1. Test methods are so focused that they fail to tell a complete story.
1. Separating assertions provides a fragmented picture of what the method-under-test does.
1. Separating sequential assertions creates potential testing holes.  For example:
```
if(customer.shouldEmail()) {
    emailer.send(customer, invoice);
}else{
    mailer.mail(customer, invoice);
}
```
and
```
emailer.send(customer, invoice);
mailer.mail(customer, invoice);
```
without the if/else both pass in the `SeparateBillerTest` test.  Because there is only one assertion per test it's 
impossilbe to determine that the other case was not called.  

# 2. One Path
## Pros
1. Tests clearly documents how a method is used under one set of conditions.
1. Provides wholistic test accountability for the one set of conditions.

## Cons
1. May be longer.  However this may be an indication that your method-under-test is too long.

# 3. All Paths
## Pros
1. Everything is all in one test method.

## Cons
1. Clean up, traditionally done in the @After teardown method, may need to be done between each scenario to 
prevent state corruption.
1. If one scenario fails the following scenarios will not be run.
1. Continuious Integration failure messages may be more generic and require more research if test methods 
do too many things.
1. Test method names are less expressive because they need to describe all interactions instead of a specific scenario.


