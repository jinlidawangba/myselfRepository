// MyAIDL.aidl
package comb.ex.administrator.aidltask;

// Declare any non-default types here with import statements
parcelable person;
interface MyAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void ok(String info);
    void erro();
}
