import android.content.Context
import android.os.Environment
import android.support.multidex.MultiDexApplication
import android.widget.Toast
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ExcelExporter(private val context: Context)  : MultiDexApplication(){

    fun exportToExcel(fileName: String, data: List<List<String>>) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")

        for ((rowIndex, rowData) in data.withIndex()) {
            val row: Row = sheet.createRow(rowIndex)

            for ((colIndex, cellData) in rowData.withIndex()) {
                val cell: Cell = row.createCell(colIndex)
                cell.setCellValue(cellData)
            }
        }

        // Save the Excel file to external storage
        saveExcelFile(workbook, fileName)
    }

    private fun saveExcelFile(workbook: XSSFWorkbook, fileName: String) {

        val directory = Environment.DIRECTORY_DOWNLOADS

        val file = File(
            Environment.getExternalStoragePublicDirectory(directory),
            fileName
        )

        try {
            val fileOutputStream = FileOutputStream(file)
            workbook.write(fileOutputStream)
            fileOutputStream.close()

            Toast.makeText(context, "Excel file saved to: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()

            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
