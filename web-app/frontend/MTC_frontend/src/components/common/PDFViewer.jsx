import { Document, Page, pdfjs } from 'react-pdf';
import 'pdfjs-dist/web/pdf_viewer.css';

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url
).toString();

export default function PDFViewer({ fileUrl }) {
    console.log('PDFViewer fileUrl:', fileUrl);
    return (
        <div style={{ border: '1px solid #ccc', padding: '8px' }}>
            {/*<Document*/}
            {/*    file={fileUrl}*/}
            {/*    onLoadError={err => console.error('PDF load error:', err)}*/}
            {/*>*/}
            {/*    <Page pageNumber={1} />*/}
            {/*</Document>*/}
            <iframe
                src={encodeURI(fileUrl)}
                width="100%"
                height="600"
                style={{ border: 'none' }}
                title="PDF Viewer"
            />
        </div>
    );
}
